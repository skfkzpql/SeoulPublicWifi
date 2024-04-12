<!-- detail.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 자세히 보기</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>와이파이 정보 자세히 보기</h1>
	<%@ include file="header.jsp"%>
	<div class="bookmarkDiv">
		<select id="bookmarkGroupSelect">
			<option value="" selected disabled hidden>북마크 그룹 이름 선택</option>
		</select>
		<button id="addBookmarkBtn">북마크 추가</button>
	</div>
	<table id="wifiInfoTable">
		<tr>
			<th>거리(Km)</th>
			<td id="distance"></td>
		</tr>
		<tr>
			<th>관리번호</th>
			<td id="mgrNo"></td>
		</tr>
		<tr>
			<th>자치구</th>
			<td id="wrdoFc"></td>
		</tr>
		<tr>
			<th>와이파이명</th>
			<td id="wifiName"></td>
		</tr>
		<tr>
			<th>도로명주소</th>
			<td id="roadAddress"></td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td id="detailAddress"></td>
		</tr>
		<tr>
			<th>설치위치(층)</th>
			<td id="installationFloor"></td>
		</tr>
		<tr>
			<th>설치유형</th>
			<td id="installationType"></td>
		</tr>
		<tr>
			<th>서비스구분</th>
			<td id="serviceType"></td>
		</tr>
		<tr>
			<th>망종류</th>
			<td id="networkType"></td>
		</tr>
		<tr>
			<th>설치년도</th>
			<td id="installationYear"></td>
		</tr>
		<tr>
			<th>실내외구분</th>
			<td id="indoorOutdoorType"></td>
		</tr>
		<tr>
			<th>WIFI접속환경</th>
			<td id="wifiEnvironment"></td>
		</tr>
		<tr>
			<th>X좌표</th>
			<td id="latitude"></td>
		</tr>
		<tr>
			<th>Y좌표</th>
			<td id="longitude"></td>
		</tr>
		<tr>
			<th>작업일자</th>
			<td id="workDateTime"></td>
		</tr>
	</table>

	<script>
        // 페이지 로드 시 실행되는 함수
        function onLoad() {
        	showBookmarkGroup();
            showDetail();
            document.getElementById("addBookmarkBtn").addEventListener("click", addBookmark);
        }

        // 북마크 그룹 정보 가져오는 함수
        function showBookmarkGroup() {
            fetch("ShowBookmarkGroupServlet")
                .then(response => response.json())
                .then(data => {
                    data.forEach(group => {
                        const option = document.createElement("option");
                        option.value = group.bookmark_group_id;
                        option.text = group.group_name;
                        document.getElementById("bookmarkGroupSelect").appendChild(option);
                    });
                })
                .catch(error => console.error("북마크 그룹 가져오기 에러:", error));
        }

        // 와이파이 상세 정보 가져오는 함수
        
        function showDetail() {
		    var urlParams = new URLSearchParams(window.location.search);
		    var mgrNo = urlParams.get('mgrNo');
		    var distance = urlParams.get('distance');
		    var wifiInfoInputDTO = {
		        mgrNo: mgrNo
		    };
		
		    fetch("ShowDetailWifiInfoServlet", {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json; charset=UTF-8'
		        },
		        body: JSON.stringify(wifiInfoInputDTO)
		    })
		    .then(response => response.json())
		    .then(wifiInfo => {
		        document.getElementById("distance").innerText = distance;
		        document.getElementById("mgrNo").innerText = mgrNo;
		        document.getElementById("wrdoFc").innerText = wifiInfo.X_SWIFI_WRDOFC;
		        document.getElementById("wifiName").innerText = wifiInfo.X_SWIFI_MAIN_NM;
		        document.getElementById("roadAddress").innerText = wifiInfo.X_SWIFI_ADRES1;
		        document.getElementById("detailAddress").innerText = wifiInfo.X_SWIFI_ADRES2;
		        document.getElementById("installationFloor").innerText = wifiInfo.X_SWIFI_INSTL_FLOOR;
		        document.getElementById("installationType").innerText = wifiInfo.X_SWIFI_INSTL_TY;
		        document.getElementById("serviceType").innerText = wifiInfo.X_SWIFI_SVC_SE;
		        document.getElementById("networkType").innerText = wifiInfo.X_SWIFI_CMCWR;
		        document.getElementById("installationYear").innerText = wifiInfo.X_SWIFI_CNSTC_YEAR;
		        document.getElementById("indoorOutdoorType").innerText = wifiInfo.X_SWIFI_INOUT_DOOR;
		        document.getElementById("wifiEnvironment").innerText = wifiInfo.X_SWIFI_REMARS3;
		        document.getElementById("latitude").innerText = wifiInfo.LAT;
		        document.getElementById("longitude").innerText = wifiInfo.LNT;
		        document.getElementById("workDateTime").innerText = wifiInfo.WORK_DTTM;
		    })
		    .catch(error => console.error("Failed to fetch wifi detail:", error));
		}
		
     	// 북마크 추가 함수
        function addBookmark() {
        	var urlParams = new URLSearchParams(window.location.search);
            var mgrNo = urlParams.get('mgrNo');
        	if (!mgrNo) {
				alert("관리번호가 없습니다. 데이터를 확인해 주세요.");
				return;
			}
            var bookmarkGroupSelect = document.getElementById("bookmarkGroupSelect");
            var selectedGroupId = bookmarkGroupSelect.options[bookmarkGroupSelect.selectedIndex].value;
            if (!selectedGroupId) {
            	alert("북마크 그룹을 선택해 주세요.");
            	return;
			}
            
            var bookmarkData = {
            	bookmark_group_id: selectedGroupId,
            	wifi_info_id: mgrNo
                
            };
        
            fetch("AddBookmarkServlet", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                },
                body: JSON.stringify(bookmarkData)
            })
            .then(response => response.text())
            .then(result => {
            	if (result === "1") {
                   alert("북마크를 추가 했습니다.");
                } else if (result === "-3") {
                    alert("북마크 추가에 실패했습니다.(이미 북마크에 추가되어 있습니다.)");
                } else {
                    alert("북마크 추가 실패");
                }
            })
            .catch(error => console.error("북마크 추가 에러:", error));
        }
        // 페이지 로드 시 실행
        window.onload = onLoad;
    </script>
</body>
</html>
