package controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.BookmarkGroupDTO;
import service.BookmarkGroupService;

@WebServlet("/EditBookmarkGroupServlet")
public class EditBookmarkGroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String jsonBookmarkGroupDTO = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    	Gson gson = new Gson();
    	BookmarkGroupDTO bookmarkGroupDTO = gson.fromJson(jsonBookmarkGroupDTO, BookmarkGroupDTO.class);

        BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();

        int result = bookmarkGroupService.updateBookmarkGroup(bookmarkGroupDTO);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(result));
    }
}
