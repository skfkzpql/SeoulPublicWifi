<!-- bookmark.jsp -->
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
    <div class="bookmarkDiv">
        <button id="deleteAllBookmarkBtn">북마크 전체 삭제</button>
    </div>
    
    <table id="bookmarkTable">
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 그룹 이름</th>
                <th>와이파이명</th>
                <th>등록일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody id="bookmarkList"></tbody>
    </table>
    
    <script>
        window.onload = function() {
            showBookmark();
        };
    
        function showBookmark() {
            fetch("ShowBookmarkServlet")
                .then(response => response.json())
                .then(bookmarks => {
                    const bookmarkList = document.getElementById("bookmarkList");
                    bookmarkList.innerHTML = "";
                    
                    bookmarks.forEach(bookmark => {
                        const row = document.createElement("tr");
                        
                        // ID
                        const idCell = document.createElement("td");
                        idCell.textContent = bookmark.bookmark_id;
                        row.appendChild(idCell);

                        // 북마크 그룹 이름
                        const groupNameCell = document.createElement("td");
                        groupNameCell.textContent = bookmark.group_name;
                        row.appendChild(groupNameCell);

                        // 와이파이명
                        const wifiNameCell = document.createElement("td");
                        wifiNameCell.textContent = bookmark.X_SWIFI_MAIN_NM;
                        row.appendChild(wifiNameCell);

                        // 등록일자
                        const registrationDateCell = document.createElement("td");
                        registrationDateCell.textContent = bookmark.registration_date;
                        row.appendChild(registrationDateCell);

                        // 삭제 링크
                        const deleteLink = document.createElement("a");
                        deleteLink.href = "#"; // 클릭 시 페이지 이동 방지
                        deleteLink.textContent = "삭제";
                        deleteLink.onclick = function() {
                            deleteBookmark(bookmark.bookmark_id);
                        };
                        actionsCell.appendChild(deleteLink);

                        row.appendChild(actionsCell);

                        bookmarkList.appendChild(row);
                    });
                })
                .catch(error => console.error("북마크 로드 실패:", error));
        }

        function deleteBookmark(bookmarkId) {
            if (confirm("북마크를 삭제하시겠습니까?")) {
                fetch("DeleteBookmarkServlet?bookmarkId=" + bookmarkId, {
                    method: "DELETE"
                })
                .then(response => {
                    if (response.ok) {
                        // 삭제 성공 시 페이지 새로고침
                        location.reload();
                    } else {
                        throw new Error("북마크 삭제에 실패했습니다.");
                    }
                })
                .catch(error => console.error("북마크 삭제 에러:", error));
            }
        }

        // 북마크 전체 삭제 버튼 클릭 시
        document.getElementById("deleteAllBookmarkBtn").addEventListener("click", function() {
            if (confirm("모든 북마크를 삭제하시겠습니까?")) {
                fetch("DeleteAllBookmarkServlet", {
                    method: "DELETE"
                })
                .then(response => {
                    if (response.ok) {
                        // 삭제 성공 시 페이지 새로고침
                        location.reload();
                    } else {
                        throw new Error("모든 북마크 삭제에 실패했습니다.");
                    }
                })
                .catch(error => console.error("모든 북마크 삭제 에러:", error));
            }
        });
    </script>
</body>
</html>
