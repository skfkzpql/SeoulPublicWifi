package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BookmarkDAO;
import dao.DBConnector;
import dao.DTOMapper;
import dto.BookmarkDTO;

public class BookmarkService implements BookmarkDAO{

	@Override
	public int insertBookmark(BookmarkDTO bookmarkDTO) {
		int result = -1;
		String selectSql = "SELECT COUNT(*) FROM Bookmark WHERE bookmark_group_id = ? and wifi_info_id = ?";
		String insertSql = "INSERT INTO Bookmark (bookmark_group_id, wifi_info_id) VALUES (?, ?)";
		
		try (Connection conn = DBConnector.connect();
	         PreparedStatement selectPstmt = conn.prepareStatement(selectSql);
			 PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
			
			selectPstmt.setInt(1, bookmarkDTO.getBookmark_group_id());
			selectPstmt.setString(2, bookmarkDTO.getWifi_info_id());
			ResultSet rs = selectPstmt.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return -3;
	        }
			
	        insertPstmt.setInt(1, bookmarkDTO.getBookmark_group_id());
	        insertPstmt.setString(2, bookmarkDTO.getWifi_info_id());
			result = insertPstmt.executeUpdate();
			
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
			return result;
	    }

	@Override
	public void recreateBookmark() {
		String dropTableSql = "DROP TABLE IF EXISTS Bookmark";
		String createTableSql = "CREATE TABLE IF NOT EXISTS Bookmark ("
		        + "bookmark_id INTEGER PRIMARY KEY AUTOINCREMENT,"
		        + "bookmark_group_id INTEGER NOT NULL,"
		        + "wifi_info_id TEXT NOT NULL,"
		        + "registration_date TEXT DEFAULT (strftime('%Y-%m-%dT%H:%M:%S', 'now', 'localtime'))"
		        + ")";

        try (Connection conn = DBConnector.connect();
             PreparedStatement dropPstmt = conn.prepareStatement(dropTableSql);
             PreparedStatement createPstmt = conn.prepareStatement(createTableSql)) {
        	dropPstmt.executeUpdate();
        	createPstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void deleteBookmark(BookmarkDTO bookmarkDTO) {
		String deleteSql = "DELETE FROM Bookmark WHERE bookmark_id = ?";
		try (Connection conn = DBConnector.connect();
	         PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {

			pstmt.setInt(1, bookmarkDTO.getBookmark_id());
            pstmt.execute();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public List<BookmarkDTO> selectAllBookmark() {
		List<BookmarkDTO> bookmarks = new ArrayList<>();
		String selectSql = "SELECT Bookmark.bookmark_id, Bookmark_group.group_name, WifiInfo.X_SWIFI_MAIN_NM, Bookmark.registration_date " +
                "FROM Bookmark " +
                "INNER JOIN Bookmark_group ON Bookmark.bookmark_group_id = Bookmark_group.bookmark_group_id " +
                "INNER JOIN WifiInfo ON Bookmark.wifi_info_id = WifiInfo.X_SWIFI_MGR_NO;";
	
		try (Connection conn = DBConnector.connect();
		  PreparedStatement pstmt = conn.prepareStatement(selectSql);
		  ResultSet rs = pstmt.executeQuery()) {
		 while (rs.next()) {
		     BookmarkDTO bookmarkDTO = DTOMapper.mapResultSetToDTO(rs, BookmarkDTO.class);
		     bookmarks.add(bookmarkDTO);
		 }
		} catch (SQLException e) {
		 e.printStackTrace();
		}
		return bookmarks;
	}

}
