package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import dao.DBConnector;
import dao.DTOMapper;
import dao.WifiInfoDAO;
import dto.WifiInfoDTO;
import dto.WifiInfoInputDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vo.WifiInfoApiResponseVO;
import vo.WifiInfoVO;

public class WifiInfoService implements WifiInfoDAO {

	@Override
	public int insertWifiInfo() {
		int totalAffectedRows = 0;
		String dropSql = "DROP TABLE IF EXISTS WifiInfo;";

		String createSql = "CREATE TABLE IF NOT EXISTS WifiInfo (\n" +
	             "    X_SWIFI_MGR_NO TEXT PRIMARY KEY,\n" +
	             "    X_SWIFI_WRDOFC TEXT,\n" +
	             "    X_SWIFI_MAIN_NM TEXT,\n" +
	             "    X_SWIFI_ADRES1 TEXT,\n" +
	             "    X_SWIFI_ADRES2 TEXT,\n" +
	             "    X_SWIFI_INSTL_FLOOR TEXT,\n" +
	             "    X_SWIFI_INSTL_TY TEXT,\n" +
	             "    X_SWIFI_INSTL_MBY TEXT,\n" +
	             "    X_SWIFI_SVC_SE TEXT,\n" +
	             "    X_SWIFI_CMCWR TEXT,\n" +
	             "    X_SWIFI_CNSTC_YEAR TEXT,\n" +
	             "    X_SWIFI_INOUT_DOOR TEXT,\n" +
	             "    X_SWIFI_REMARS3 TEXT,\n" +
	             "    LAT TEXT,\n" +
	             "    LNT TEXT,\n" +
	             "    WORK_DTTM TEXT\n" +
	             ");";

		String insertSql = "INSERT INTO WifiInfo "
                + "(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, "
                + "X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, "
                + "X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, "
                + "X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, "
                + "X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnector.connect();
	    	 PreparedStatement dropPstmt = conn.prepareStatement(dropSql);
	    	 PreparedStatement createPstmt = conn.prepareStatement(createSql);
	    	 PreparedStatement insertPstmt = conn.prepareStatement(insertSql);){

	    	dropPstmt.execute();
	    	createPstmt.execute();

	        int listTotalCount = loadFromAPI(1, 1).getTbPublicWifiInfo().getList_total_count();

	        conn.setAutoCommit(false);

	        for (int i = 0; i <= listTotalCount / 1000; i++) {
	            int start = i * 1000 + 1;
	            int end = Math.min(start + 999, listTotalCount);

	            WifiInfoApiResponseVO wifiInfoApiResponseVO = loadFromAPI(start, end);

	            for (WifiInfoVO wifi : wifiInfoApiResponseVO.getTbPublicWifiInfo().getRow()) {
	            	insertPstmt.setString(1, wifi.getX_SWIFI_MGR_NO());
	            	insertPstmt.setString(2, wifi.getX_SWIFI_WRDOFC());
	            	insertPstmt.setString(3, wifi.getX_SWIFI_MAIN_NM());
	            	insertPstmt.setString(4, wifi.getX_SWIFI_ADRES1());
	            	insertPstmt.setString(5, wifi.getX_SWIFI_ADRES2());
	            	insertPstmt.setString(6, wifi.getX_SWIFI_INSTL_FLOOR());
	            	insertPstmt.setString(7, wifi.getX_SWIFI_INSTL_TY());
	            	insertPstmt.setString(8, wifi.getX_SWIFI_INSTL_MBY());
	    	        insertPstmt.setString(9, wifi.getX_SWIFI_SVC_SE());
	    	        insertPstmt.setString(10, wifi.getX_SWIFI_CMCWR());
	    	        insertPstmt.setString(11, wifi.getX_SWIFI_CNSTC_YEAR());
	    	        insertPstmt.setString(12, wifi.getX_SWIFI_INOUT_DOOR());
	    	        insertPstmt.setString(13, wifi.getX_SWIFI_REMARS3());
	    	        insertPstmt.setString(14, wifi.getLAT());
	    	        insertPstmt.setString(15, wifi.getLNT());
	    	        insertPstmt.setString(16, wifi.getWORK_DTTM());
	    	        insertPstmt.addBatch();
	    	    }

	            int[] batchResult = insertPstmt.executeBatch();
	            totalAffectedRows += Arrays.stream(batchResult).sum();
	            conn.commit();
	            insertPstmt.clearBatch();
	        }

	        if (totalAffectedRows != listTotalCount) {
	            System.out.println("삽입한 데이터의 개수와 API에서 가져온 데이터의 개수가 다릅니다.");
	        }

	        return totalAffectedRows;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return totalAffectedRows;
	}


	private WifiInfoApiResponseVO loadFromAPI(int start, int end) {
        WifiInfoApiResponseVO wifiInfoApiResponseVO = null;
        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(buildApiURL(start, end)).build();
        try (Response response = client.newCall(request).execute()) {
            wifiInfoApiResponseVO = new Gson().fromJson(response.body().string(), WifiInfoApiResponseVO.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return wifiInfoApiResponseVO;
    }

	private String buildApiURL(int start, int end) {
		final String API_KEY = "6c4b68586d736b6635366879576272";
		final String API_TYPE = "json";
		final String API_SERVICE = "TbPublicWifiInfo";
		final String API_URL = "http://openapi.seoul.go.kr:8088/";

		return API_URL + API_KEY + "/" + API_TYPE + "/" + API_SERVICE + "/" + start + "/" + end + "/";
    }


	@Override
	public WifiInfoDTO selectWifiInfo(WifiInfoInputDTO wifiInfoInputDTO) {
	    WifiInfoDTO wifiInfoDTO2 = null;
	    String sql = "SELECT * FROM WifiInfo WHERE X_SWIFI_MGR_NO = ?";

	    try (Connection conn = DBConnector.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql);) {

	        pstmt.setString(1, wifiInfoInputDTO.getMgrNo());
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	            	wifiInfoDTO2 = DTOMapper.mapResultSetToDTO(rs, WifiInfoDTO.class);
	            } else {
	                System.out.println("데이터 조회 실패");
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return wifiInfoDTO2;
	}



	@Override
	public List<WifiInfoDTO> select20NearestWifiInfo(WifiInfoInputDTO wifiInfoInputDTO) {
	    List<WifiInfoDTO> wifiInfoList = new ArrayList<>();
	    String sql = "SELECT *, "
	               + "6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(LAT)) * COS(RADIANS(LNT) - RADIANS(?))"
	               + " + SIN(RADIANS(?)) * SIN(RADIANS(LAT))) AS distance "
	               + "FROM WifiInfo "
	               + "WHERE LAT <> 0.0 AND LNT <> 0.0 "
	               + "ORDER BY distance ASC "
	               + "LIMIT 20;";

	    try (Connection conn = DBConnector.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setDouble(1, wifiInfoInputDTO.getLatInput());
	        pstmt.setDouble(2, wifiInfoInputDTO.getLntInput());
	        pstmt.setDouble(3, wifiInfoInputDTO.getLatInput());

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                WifiInfoDTO wifiInfoDTO = DTOMapper.mapResultSetToDTO(rs, WifiInfoDTO.class);
	                wifiInfoList.add(wifiInfoDTO);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return wifiInfoList;
	}

}
