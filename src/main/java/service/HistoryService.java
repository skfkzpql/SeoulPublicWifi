package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DBConnector;
import dao.DTOMapper;
import dao.HistoryDAO;
import dto.HistoryDTO;
import dto.PaginatedHistoryDTO;

public class HistoryService implements HistoryDAO {

    @Override
    public void insertHistory(HistoryDTO historyDTO) {
        try (Connection conn = DBConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO History (lat, lnt) VALUES (?, ?)")) {
            pstmt.setString(1, historyDTO.getLat());
            pstmt.setString(2, historyDTO.getLnt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteHistory(HistoryDTO historyDTO) {
        String sql = "DELETE FROM History WHERE history_id = ?";
        try (Connection conn = DBConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, historyDTO.getHistory_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PaginatedHistoryDTO selectHistoryPagination(int startIndex, int pageSize) {
        String countSql = "SELECT COUNT(*) FROM History";
        String selectSql = "SELECT * FROM History ORDER BY time_stamp DESC, history_id DESC LIMIT ?, ?";

        int totalRecords = 0;
        
        List<HistoryDTO> historyList = new ArrayList<>();
        try (Connection conn = DBConnector.connect();
             PreparedStatement countPstmt = conn.prepareStatement(countSql);
             PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
            
            // 전체 레코드 수 가져오기
            try (ResultSet countRs = countPstmt.executeQuery()) {
                if (countRs.next()) {
                    totalRecords = countRs.getInt(1);
                }
            }

            // 페이징된 결과 가져오기
            selectPstmt.setInt(1, startIndex);
            selectPstmt.setInt(2, pageSize);
            
            try (ResultSet rs = selectPstmt.executeQuery()) {
                while (rs.next()) {
                    HistoryDTO historyDTO = DTOMapper.mapResultSetToDTO(rs, HistoryDTO.class);
                    historyList.add(historyDTO);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        
        PaginatedHistoryDTO paginatedHistory = new PaginatedHistoryDTO();
        paginatedHistory.setHistoryList(historyList);
        paginatedHistory.setCurrentPage(startIndex / pageSize + 1);
        paginatedHistory.setTotalPages(totalPages);
        
        return paginatedHistory;
    }


    @Override
    public void recreateHistory() {
        String dropTableSql = "DROP TABLE IF EXISTS History";
        String createTableSql = "CREATE TABLE IF NOT EXISTS History ("
                + "history_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "lat TEXT,"
                + "lnt TEXT,"
                + "time_stamp TEXT DEFAULT (strftime('%Y-%m-%dT%H:%M:%S', 'now', 'localtime'))"
                + ")";
        try (Connection conn = DBConnector.connect();
             PreparedStatement pstmtDrop = conn.prepareStatement(dropTableSql);
             PreparedStatement pstmtCreate = conn.prepareStatement(createTableSql)) {
            pstmtDrop.executeUpdate();
            pstmtCreate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
