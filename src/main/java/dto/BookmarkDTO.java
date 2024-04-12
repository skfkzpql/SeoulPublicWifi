package dto;

import lombok.Data;

@Data
public class BookmarkDTO {
	private int bookmark_id;
	private int bookmark_group_id;
	private String wifi_info_id;
	private String group_name;
	private String X_SWIFI_MAIN_NM;
	private String registration_date;
}
