package controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.HistoryDAO;
import dto.HistoryDTO;
import service.HistoryService;


@WebServlet("/DeleteHistoryServlet")
public class DeleteHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteHistoryServlet() {
        super();
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonHistoryDTO = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        HistoryDTO historyDTO = gson.fromJson(jsonHistoryDTO, HistoryDTO.class);
        HistoryDAO historyDAO = new HistoryService();
        historyDAO.deleteHistory(historyDTO);
        
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
