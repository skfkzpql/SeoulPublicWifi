package dao;


import java.util.List;

import dto.BookmarkGroupDTO;


public interface BookmarkGroupDAO {
	int insertBookmarkGroup(BookmarkGroupDTO bookmarkGroupDTO);
    int updateBookmarkGroup(BookmarkGroupDTO bookmarkGroupDTO);
    void recreateBookmarkGroup();
    void deleteBookmarkGroup(BookmarkGroupDTO bookmarkGroupDTO);
    List<BookmarkGroupDTO> selectBookmarkGroup();
}
