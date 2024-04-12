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

/**
 * Servlet implementation class ShowNearbyWifiInfoServelet
 */
@WebServlet("/AddHistoryServlet")
public class AddHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String jsonHistoryDTO = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	    Gson gson = new Gson();
	    HistoryDTO historyDTO = gson.fromJson(jsonHistoryDTO, HistoryDTO.class);

	    response.setCharacterEncoding("UTF-8");

	    HistoryDAO historyDAO = new HistoryService();
	    historyDAO.insertHistory(historyDTO);
	}

}
