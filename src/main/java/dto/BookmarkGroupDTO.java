package dto;

import lombok.Data;

@Data
public class BookmarkGroupDTO {
	private int bookmark_group_id;
    private String group_name;
    private int sequence;
    private String create_date;
    private String update_date;
}
