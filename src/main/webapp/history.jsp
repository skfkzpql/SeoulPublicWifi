<!-- hisotry.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>위치 히스토리 목록</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1>위치 히스토리 목록</h1>
    <%@ include file="header.jsp"%>

    <!-- 전체 삭제 버튼 -->
    <button onclick="deleteAllHistory()" style="margin-top: 20px;">전체 삭제</button>

    <table id="historyTable">
        <thead>
            <tr>
                <th>ID</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>조회일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody id="historyTableBody">
            <!-- 이곳에 히스토리 데이터가 추가될 것입니다. -->
        </tbody>
    </table>

    <!-- 페이징 버튼 -->
    <div id="pagination">
        <!-- 페이지 번호를 보여줄 곳 -->
    </div>

    <script>
        var currentPage = 1; // 현재 페이지 번호

        // 페이지 로드 시 초기 히스토리 목록 불러오기
        loadHistory(currentPage);

        // 히스토리 목록을 가져오는 함수
        function loadHistory(pageNumber) {
            fetch("ShowHistoryServlet?pageNumber=" + pageNumber)
                .then(response => response.json())
                .then(paginatedHistory => {
                    var historyTableBody = document.getElementById("historyTableBody");
                    var pagination = document.getElementById("pagination");

                    // 히스토리 데이터 표시
                    historyTableBody.innerHTML = ""; // 이전 데이터 삭제
                    if (paginatedHistory.historyList.length === 0) {
                        // 데이터가 없는 경우 메시지 표시
                        var noDataMessage = "<tr><td colspan='5' style='text-align: center;'>히스토리 목록이 없습니다.</td></tr>";
                        historyTableBody.innerHTML += noDataMessage;
                    } else {
                        // 데이터가 있는 경우 테이블에 추가
                        paginatedHistory.historyList.forEach(history => {
                            var row = "<tr>";
                            row += "<td>" + history.history_id + "</td>";
                            row += "<td>" + history.lat + "</td>";
                            row += "<td>" + history.lnt + "</td>";
                            row += "<td>" + history.time_stamp + "</td>";
                            // 비고 열에 있는 검색 버튼을 클릭했을 때 index.jsp로 이동하고 lat, lnt 값을 전달하고 showNearbyWifiInfo() 함수 실행
                            row += "<td><form id='searchForm_" + history.history_id + "' action='index.jsp' method='get'>"
                                + "<input type='hidden' name='latParam' value='" + history.lat + "' />"
                                + "<input type='hidden' name='lntParam' value='" + history.lnt + "' />"
                                + "<input type='button' value='검색' onclick='searchNearbyFromHistory(" + history.history_id + ", " + history.lat + ", " + history.lnt + ")' /></form>"
                                + "<button onclick='deleteHistory(" + history.history_id + ")'>삭제</button></td>";
                            row += "</tr>";
                            historyTableBody.innerHTML += row;
                        });

                        // 페이징 버튼 생성
                        pagination.innerHTML = ""; // 이전 버튼 삭제
                        for (var i = 1; i <= paginatedHistory.totalPages; i++) {
                            var button = "<button onclick='loadHistory(" + i + ")'>" + i + "</button>";
                            pagination.innerHTML += button;
                        }
                    }
                })
                .catch(error => console.error('히스토리 가져오기 에러:', error));
        }

        // 히스토리 삭제 함수
        function deleteHistory(historyId) {
            if (confirm("이 항목을 삭제하시겠습니까?")) {
                var data = {
                    history_id: historyId
                };
                fetch("DeleteHistoryServlet", {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json; charset=UTF-8"
                    },
                    body: JSON.stringify(data)
                })
                    .then(response => {
                        if (response.ok) {
                            // 삭제 성공 시 페이지를 새로고침하여 변경된 목록을 표시합니다.
                            loadHistory(currentPage);
                        } else {
                            throw new Error("히스토리 삭제에 실패했습니다.");
                        }
                    })
                    .catch(error => console.error('히스토리 삭제 에러:', error));
            }
        }

        // 전체 히스토리 삭제 함수
        function deleteAllHistory() {
            if (confirm("모든 히스토리를 삭제하시겠습니까?")) {
                fetch("DeleteAllHistoryServlet", {
                    method: "DELETE"
                })
                    .then(response => {
                        if (response.ok) {
                            // 삭제 성공 시 페이지를 새로고침하여 변경된 목록을 표시합니다.
                            loadHistory(currentPage);
                        } else {
                            throw new Error("모든 히스토리 삭제에 실패했습니다.");
                        }
                    })
                    .catch(error => console.error('모든 히스토리 삭제 에러:', error));
            }
        }

        function searchNearbyFromHistory(historyId, lat, lnt) {
            document.getElementById("searchForm_" + historyId).submit();
        }
    </script>

</body>
</html>




