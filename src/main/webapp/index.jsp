<!-- index.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<%@ include file="header.jsp"%>
	<p>
		LAT: <input type="text" id="latInput"
			value="<%= (request.getParameter("latInput") != null) ? request.getParameter("latInput") : "0.0" %>">
		, LNT: <input type="text" id="lntInput"
			value="<%= (request.getParameter("lntInput") != null) ? request.getParameter("lntInput") : "0.0" %>">
		<button id="getLocationBtn">내 위치 가져오기</button>
		<button id="showNearbyBtn">근처 WIFI 정보 보기</button>
	</p>

	<table id="wifiInfoTable">
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody id="wifiInfoTableBody">
			<tr>
				<td colspan="16" style="text-align: center;">위치 정보를 입력한 후에 조회해
					주세요.</td>
			</tr>
		</tbody>
	</table>

	<!-- 상세 정보 보기를 위한 폼 -->
	<form id="detailForm" action="detail.jsp" method="post">
		<input type="hidden" id="wifiId" name="wifiId">
	</form>

	<script>
	    document.addEventListener("DOMContentLoaded", function() {
	    	var urlParams = new URLSearchParams(window.location.search);
		    var latParam = urlParams.get('latParam');
		    var lntParam = urlParams.get('lntParam');
		    if (latParam && lntParam) {
		    	document.getElementById("latInput").value = latParam;
		    	document.getElementById("lntInput").value = lntParam;
	        	showNearbyWifiInfo();
	        }
	        document.getElementById("getLocationBtn").addEventListener("click", getLocation);
	        
	        document.getElementById("showNearbyBtn").addEventListener("click", function() {
	            if (document.getElementById("latInput").value && document.getElementById("lntInput").value) {
					var latInput = document.getElementById("latInput").value;
					var lntInput = document.getElementById("lntInput").value;
	                addHistory();
	                showNearbyWifiInfo();
	            }
	        });
	        
	    });
	
	    function getLocation() {
	        if (navigator.geolocation) {
	            navigator.geolocation.getCurrentPosition(showPosition);
	        } else {
	            console.log("Geolocation is not supported by this browser.");
	        }
	    }
	
	    function showPosition(position) {
	        document.getElementById("latInput").value = position.coords.latitude;
	        document.getElementById("lntInput").value = position.coords.longitude;
	    }
	
	    function addHistory() {
	        var historyDTO = {
	            lat: document.getElementById("latInput").value,
	            lnt: document.getElementById("lntInput").value
	        };
	        fetch("AddHistoryServlet", {
	            method: "POST",
	            headers: {
	                "Content-Type": "application/json; charset=UTF-8"
	            },
	            body: JSON.stringify(historyDTO)
	        })
	        .then(response => {
	            if (!response.ok) {
	                throw new Error('히스토리 추가에 실패했습니다.');
	            }
	        })
	        .catch(error => console.error("히스토리 추가 에러:", error));
	    }
	
	    function showNearbyWifiInfo(lat, lnt) {
	        var wifiInfoInputDTO = {
	            latInput: document.getElementById("latInput").value,
	            lntInput: document.getElementById("lntInput").value
	        };
	        
	        var jsonWifiInfoInputDTO = JSON.stringify(wifiInfoInputDTO);
	        fetch("ShowNearbyWifiInfoServlet", {
	            method: "POST",
	            headers: {
	                "Content-Type": "application/json; charset=UTF-8"
	            },
	            body: jsonWifiInfoInputDTO
	        })
	        .then(response => response.json())
	        .then(nearbyWifiInfo => displayWifiInfo(nearbyWifiInfo))
	        .catch(error => console.error("근처 WIFI 정보 보기 에러:", error));
	    }
	
	    function displayWifiInfo(wifiInfoList) {
	        var tableBody = document.getElementById("wifiInfoTableBody");
	        tableBody.innerHTML = "";
	        
	        wifiInfoList.forEach(function(wifiInfo) {
	            var distance = wifiInfo.distance.toFixed(4);
	            var wifiName = wifiInfo.X_SWIFI_MAIN_NM;
	            var wifiMrgNo = wifiInfo.X_SWIFI_MGR_NO;
	            var wifiNameLink = document.createElement("a");
	            wifiNameLink.href = "detail.jsp?distance=" + distance + "&mgrNo=" + wifiMrgNo;
	            wifiNameLink.innerText = wifiName;
	            
	            var row = tableBody.insertRow();
	            row.insertCell().innerText = distance;
	            row.insertCell().innerText = wifiMrgNo;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_WRDOFC;
	            row.appendChild(wifiNameLink);
	            row.insertCell().innerText = wifiInfo.X_SWIFI_ADRES1;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_ADRES2;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_INSTL_FLOOR;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_INSTL_TY;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_SVC_SE;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_CMCWR;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_CNSTC_YEAR;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_INOUT_DOOR;
	            row.insertCell().innerText = wifiInfo.X_SWIFI_REMARS3;
	            row.insertCell().innerText = wifiInfo.LAT;
	            row.insertCell().innerText = wifiInfo.LNT;
	            row.insertCell().innerText = wifiInfo.WORK_DTTM;
	        });
	    }
	</script>

</body>
</html>
