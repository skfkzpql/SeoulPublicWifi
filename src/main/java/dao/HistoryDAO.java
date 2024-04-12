package dao;


import dto.HistoryDTO;
import dto.PaginatedHistoryDTO;

public interface HistoryDAO {
    void insertHistory(HistoryDTO historyDTO);
    void deleteHistory(HistoryDTO historyDTO);
    PaginatedHistoryDTO selectHistoryPagination(int startIndex, int pageSize);
    void recreateHistory();
}