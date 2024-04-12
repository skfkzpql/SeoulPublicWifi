package controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.BookmarkGroupDAO;
import dto.BookmarkGroupDTO;
import service.BookmarkGroupService;

@WebServlet("/AddBookmarkGroupServlet")
public class AddBookmarkGroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String jsonBookmarkGroupDTO = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    	
    	Gson gson = new Gson();
    	BookmarkGroupDTO bookmarkGroupDTO = gson.fromJson(jsonBookmarkGroupDTO, BookmarkGroupDTO.class);
       
        BookmarkGroupDAO bookmarkGroupService = new BookmarkGroupService();

        int result = bookmarkGroupService.insertBookmarkGroup(bookmarkGroupDTO);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(result));
    }
}
