package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BookmarkGroupDAO;
import dao.DBConnector;
import dao.DTOMapper;
import dto.BookmarkGroupDTO;

public class BookmarkGroupService implements BookmarkGroupDAO {

    @Override
    public int insertBookmarkGroup(BookmarkGroupDTO bookmarkDTO) {
        int result = -1;

        try (Connection conn = DBConnector.connect();
             PreparedStatement checkPstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM Bookmark_group WHERE sequence = ?");
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Bookmark_group (group_name, sequence) VALUES (?, ?)")) {

            checkPstmt.setInt(1, bookmarkDTO.getSequence());
            try (ResultSet rs = checkPstmt.executeQuery()) {
                result = rs.getInt("count");
            }

            if (result > 0) {
                return -3;
            }

            pstmt.setString(1, bookmarkDTO.getGroup_name());
            pstmt.setInt(2, bookmarkDTO.getSequence());
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int updateBookmarkGroup(BookmarkGroupDTO bookmarkDTO) {
        int result = -1;
        try (Connection conn = DBConnector.connect();
             PreparedStatement checkPstmt = conn.prepareStatement("SELECT count(*) AS count FROM Bookmark_group WHERE bookmark_group_id <> ? and sequence = ?");
             PreparedStatement pstmt = conn.prepareStatement("UPDATE Bookmark_group SET group_name = ?, sequence = ?, update_date = strftime('%Y-%m-%dT%H:%M:%S', 'now', 'localtime') WHERE bookmark_group_id = ?")) {

            checkPstmt.setInt(1, bookmarkDTO.getBookmark_group_id());
            checkPstmt.setInt(2, bookmarkDTO.getSequence());
            try (ResultSet rs = checkPstmt.executeQuery()) {
                result = rs.getInt("count");
            }

            if (result > 0) {
                return -3;
            }

            pstmt.setString(1, bookmarkDTO.getGroup_name());
            pstmt.setInt(2, bookmarkDTO.getSequence());
            pstmt.setInt(3, bookmarkDTO.getBookmark_group_id());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void recreateBookmarkGroup() {
        try (Connection conn = DBConnector.connect()) {
            if (conn == null) {
                System.out.println("데이터베이스 연결 실패");
                return;
            }

            String dropTableSql = "DROP TABLE IF EXISTS Bookmark_group;";
            String createTableSql = "CREATE TABLE Bookmark_group ("
                    + "bookmark_group_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "group_name TEXT NOT NULL UNIQUE, "
                    + "sequence INTEGER NOT NULL UNIQUE, "
                    + "create_date TEXT DEFAULT (strftime('%Y-%m-%dT%H:%M:%S', 'now', 'localtime')), "
                    + "update_date TEXT DEFAULT '' "
                    + ");";

            conn.createStatement().execute(dropTableSql);
            conn.createStatement().execute(createTableSql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBookmarkGroup(BookmarkGroupDTO bookmarkGroupDTO) {
        try (Connection conn = DBConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Bookmark_group WHERE bookmark_group_id = ?")) {

            pstmt.setInt(1, bookmarkGroupDTO.getBookmark_group_id());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BookmarkGroupDTO> selectBookmarkGroup() {
        List<BookmarkGroupDTO> bookmarkGroupList = new ArrayList<>();

        try (Connection conn = DBConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Bookmark_group ORDER BY sequence ASC");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BookmarkGroupDTO bookmarkGroupDTO = DTOMapper.mapResultSetToDTO(rs, BookmarkGroupDTO.class);

                bookmarkGroupList.add(bookmarkGroupDTO);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarkGroupList;
    }

}
