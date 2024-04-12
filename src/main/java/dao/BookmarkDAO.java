package dao;


import java.util.List;

import dto.BookmarkDTO;


public interface BookmarkDAO {
	int insertBookmark(BookmarkDTO bookmarkDTO);
    void recreateBookmark();
    void deleteBookmark(BookmarkDTO bookmarkDTO);
    List<BookmarkDTO> selectAllBookmark();
}
