package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.HistoryDAO;
import service.HistoryService;


@WebServlet("/DeleteAllHistoryServlet")
public class DeleteAllHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteAllHistoryServlet() {
        super();
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HistoryDAO historyDAO = new HistoryService();
        historyDAO.recreateHistory();
        
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
