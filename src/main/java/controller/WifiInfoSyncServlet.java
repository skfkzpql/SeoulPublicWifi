package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.WifiInfoDAO;
import service.WifiInfoService;

@WebServlet("/WifiInfoSyncServlet")
public class WifiInfoSyncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public WifiInfoSyncServlet() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WifiInfoDAO wifiInfoDAO = new WifiInfoService();
        int insertedCount = wifiInfoDAO.insertWifiInfo();

        String message;
        if (insertedCount == 0) {
            message = "WIFI 정보를 가져오는 데 실패하였습니다.";
        } else {
            message = insertedCount + "개의 WIFI 정보를 정상적으로 저장하였습니다";
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
