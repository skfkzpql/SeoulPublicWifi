package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookmarkGroupDAO;
import service.BookmarkGroupService;

@WebServlet("/DeleteAllBookmarkGroups")
public class DeleteAllBookmarkGroupsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupService();
        bookmarkGroupDAO.recreateBookmarkGroup();

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
