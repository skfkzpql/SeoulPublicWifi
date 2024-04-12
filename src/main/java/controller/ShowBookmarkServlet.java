package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.BookmarkDAO;
import dto.BookmarkDTO;
import service.BookmarkService;


@WebServlet("/ShowBookmarkServlet")
public class ShowBookmarkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ShowBookmarkServlet() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {


        BookmarkDAO bookmarkDAO = new BookmarkService();
        List<BookmarkDTO> bookmarkList = bookmarkDAO.selectAllBookmark();

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(bookmarkList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
