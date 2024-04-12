package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookmarkDAO;
import dto.BookmarkDTO;
import service.BookmarkService;


@WebServlet("/DeleteBookmarkServlet")
public class DeleteBookmarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteBookmarkServlet() {
        super();
    }

    @Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int bookmarkId = Integer.parseInt(request.getParameter("bookmarkId"));


        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        bookmarkDTO.setBookmark_id(bookmarkId);
        BookmarkDAO bookmarkDAO = new BookmarkService();
        bookmarkDAO.deleteBookmark(bookmarkDTO);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
