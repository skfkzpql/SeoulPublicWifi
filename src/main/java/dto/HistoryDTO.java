package dto;

import lombok.Data;

@Data
public class HistoryDTO {
	private int history_id;
    private String lat;
    private String lnt;
    private String time_stamp;

}
