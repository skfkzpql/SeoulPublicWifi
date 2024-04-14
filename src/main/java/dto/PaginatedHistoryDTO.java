package dto;

import java.util.List;

import lombok.Data;

@Data
public class PaginatedHistoryDTO {
    private List<HistoryDTO> historyList;
    private int currentPage;
    private int totalPages;
}
