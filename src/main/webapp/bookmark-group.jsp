<!-- bookmark-group.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>북마크 그룹</h1>
	<%@ include file="header.jsp"%>

	<div class="bookmarkDiv">
		<button onclick="location.href='bookmark-group-add.jsp'">북마크
			그룹 이름 추가</button>
		<button id="deleteAllBookmarkGroupsBtn">북마크 그룹 전체 삭제</button>
	</div>

	<table id="bookmarkGroupTable">
		<thead>
			<tr>
				<th>ID</th>
				<th>북마크 이름</th>
				<th>순서</th>
				<th>등록일자</th>
				<th>수정일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody id="bookmarkGroupList"></tbody>
	</table>

	<script>
        // 페이지 로드 시 실행
        window.onload = function() {
        	showBookmarkGroup();
        };

        // 북마크 그룹 목록을 가져오는 함수
        function showBookmarkGroup() {
            fetch("ShowBookmarkGroupServlet")
                .then(response => response.json())
                .then(bookmarkGroups => {
                    const bookmarkGroupList = document.getElementById("bookmarkGroupList");
                    bookmarkGroupList.innerHTML = "";

                    bookmarkGroups.forEach(group => {
                        const row = document.createElement("tr");

                        // ID
                        const idCell = document.createElement("td");
                        idCell.textContent = group.bookmark_group_id;
                        row.appendChild(idCell);

                        // 북마크 이름
                        const nameCell = document.createElement("td");
                        nameCell.textContent = group.group_name;
                        row.appendChild(nameCell);

                        // 순서
                        const sequenceCell = document.createElement("td");
                        sequenceCell.textContent = group.sequence;
                        row.appendChild(sequenceCell);

                        // 등록일자
                        const createDateCell = document.createElement("td");
                        createDateCell.textContent = group.create_date;
                        row.appendChild(createDateCell);

                        // 수정일자
                        const updateDateCell = document.createElement("td");
                        updateDateCell.textContent = group.update_date;
                        row.appendChild(updateDateCell);

                        // 비고 (수정 링크)
                        const actionsCell = document.createElement("td");
                        const editLink = document.createElement("a");
                        editLink.href = "bookmark-group-edit.jsp?id=" + group.bookmark_group_id + "&name=" + encodeURIComponent(group.group_name) + "&sequence=" + group.sequence;
                        editLink.textContent = "수정";
                        actionsCell.appendChild(editLink);

                        actionsCell.appendChild(document.createTextNode(" | "));

                        // 삭제 링크
                        const deleteLink = document.createElement("a");
                        deleteLink.href = "#"; // 클릭 시 페이지 이동 방지
                        deleteLink.textContent = "삭제";
                        deleteLink.onclick = function() {
                            deleteBookmarkGroup(group.bookmark_group_id);
                        };
                        actionsCell.appendChild(deleteLink);

                        row.appendChild(actionsCell);

                        bookmarkGroupList.appendChild(row);
                    });
                })
                .catch(error => console.error("북마크 그룹 가져오기 에러:", error));
        }

        // 북마크 그룹 삭제 함수
        function deleteBookmarkGroup(id) {
            if (confirm("정말로 삭제하시겠습니까?")) {
                fetch("DeleteBookmarkGroup?id=" + id, {
                    method: "DELETE"
                })
                .then(response => {
                    if (response.ok) {
                        // 삭제 성공 시 페이지 새로고침
                        location.reload();
                    } else {
                        throw new Error("북마크 삭제 실패");
                    }
                })
                .catch(error => console.error("북마크 그룹 삭제 에러:", error));
            }
        }

        // 북마크 그룹 전체 삭제 버튼 클릭 이벤트
        document.getElementById("deleteAllBookmarkGroupsBtn").addEventListener("click", function() {
            if (confirm("모든 북마크 그룹을 삭제하시겠습니까?")) {
                fetch("DeleteAllBookmarkGroups", {
                    method: "DELETE"
                })
                .then(response => {
                    if (response.ok) {
                        // 삭제 성공 시 페이지 새로고침
                        location.reload();
                    } else {
                        throw new Error("북마크 그룹 전체 삭제 실패");
                    }
                })
                .catch(error => console.error("북마크 그룹 전체 삭제 에러:", error));
            }
        });
    </script>
</body>
</html>
