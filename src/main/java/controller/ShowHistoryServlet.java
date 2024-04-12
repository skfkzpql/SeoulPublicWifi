package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.HistoryDAO;
import dto.PaginatedHistoryDTO;
import service.HistoryService;

@WebServlet("/ShowHistoryServlet")
public class ShowHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ShowHistoryServlet() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));

        int pageSize = 10;
        int startIndex = (pageNumber - 1) * pageSize;

        HistoryDAO historyDAO = new HistoryService();
        PaginatedHistoryDTO paginatedHistory = historyDAO.selectHistoryPagination(startIndex, pageSize);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(paginatedHistory);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
