package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.BookmarkGroupDAO;
import dto.BookmarkGroupDTO;
import service.BookmarkGroupService;


@WebServlet("/ShowBookmarkGroupServlet")
public class ShowBookmarkGroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ShowBookmarkGroupServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
        

        BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupService();
        List<BookmarkGroupDTO> bookmarkGroupList = bookmarkGroupDAO.selectBookmarkGroup();
        
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(bookmarkGroupList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
