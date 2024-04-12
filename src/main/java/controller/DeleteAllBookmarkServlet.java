package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookmarkDAO;
import service.BookmarkService;

@WebServlet("/DeleteAllBookmarkServlet")
public class DeleteAllBookmarkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookmarkDAO bookmarkDAO = new BookmarkService();
        bookmarkDAO.recreateBookmark();

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
