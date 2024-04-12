package controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.WifiInfoDAO;
import dto.WifiInfoDTO;
import dto.WifiInfoInputDTO;
import service.WifiInfoService;

/**
 * Servlet implementation class ShowNearbyWifiInfoServelet
 */
@WebServlet("/ShowNearbyWifiInfoServlet")
public class ShowNearbyWifiInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowNearbyWifiInfoServlet() {
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
	    String jsonWifiInfoInputDTO = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	    Gson gson = new Gson();
	    WifiInfoInputDTO wifiInfoInputDTO = gson.fromJson(jsonWifiInfoInputDTO, WifiInfoInputDTO.class);

	    response.setCharacterEncoding("UTF-8");

	    WifiInfoDAO wifiInfoDAO = new WifiInfoService();
	    List<WifiInfoDTO> wifiInfoList = wifiInfoDAO.select20NearestWifiInfo(wifiInfoInputDTO);
	    String jsonResponse = gson.toJson(wifiInfoList);
	    response.setContentType("application/json");
	    response.getWriter().write(jsonResponse);
	}

}
