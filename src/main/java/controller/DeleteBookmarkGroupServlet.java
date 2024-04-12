package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BookmarkGroupDTO;
import service.BookmarkGroupService;


@WebServlet("/DeleteBookmarkGroup")
public class DeleteBookmarkGroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();
    @Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

    	BookmarkGroupDTO bookmarkGroupDTO = new BookmarkGroupDTO();
    	bookmarkGroupDTO.setBookmark_group_id(id);
    	bookmarkGroupService.deleteBookmarkGroup(bookmarkGroupDTO);
        response.setStatus(HttpServletResponse.SC_OK);
        
    }
}
