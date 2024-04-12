<!-- bookmark-group-edit.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 수정</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>북마크 그룹 수정</h1>
	<%@ include file="header.jsp"%>

	<form id="editBookmarkGroupForm" action="updateBookmarkGroupServlet"
		method="post">
		<input type="hidden" id="groupId" name="id"
			value="<%= request.getParameter("id") %>">
		<table>
			<tr>
				<th>북마크 이름</th>
				<td><input type="text" id="groupName" name="groupName"
					value="<%= request.getParameter("name") %>"></td>
			</tr>
			<tr>
				<th>순서</th>
				<td><input type="number" id="sequence" name="sequence"
					value="<%= request.getParameter("sequence") %>"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center"><a
					href="bookmark-group.jsp">돌아가기</a> |
					<button type="submit">수정</button></td>
			</tr>
		</table>
	</form>

	<script>
	    document.getElementById("editBookmarkGroupForm").addEventListener("submit", function(event) {
	        event.preventDefault();
	
	        var receivedName = "<%= request.getParameter("name") %>";
	        var receivedSequence = "<%= request.getParameter("sequence") %>";
	
	        var groupName = document.getElementById("groupName").value.trim();
	        var sequence = document.getElementById("sequence").value.trim();
	        var id = document.getElementById("groupId").value;
	
	        if (groupName === receivedName && sequence === receivedSequence) {
	            alert("수정 사항이 없습니다.");
	        } else {
	            var bookmarkGroupDTO = {
	            	bookmark_group_id: id,
	            	group_name: groupName,
	                sequence: sequence
	            };
	
	            fetch("EditBookmarkGroupServlet", {
	                method: "POST",
	                headers: {
	                    "Content-Type": "application/json; charset=UTF-8"
	                },
	                body: JSON.stringify(bookmarkGroupDTO)
	            })
	            .then(response => response.text())
	            .then(result => {
	                if (result === "1") {
	                    window.location.href = "bookmark-group.jsp";
	                } else if (result === "-3") {
	                    alert("데이터 수정 실패 (동일한 순서를 가진 데이터가 이미 존재합니다)");
	                } else {
	                    alert("데이터 수정 실패");
	                }
	            })
	            .catch(error => {
	                console.error("데이터 수정 에러:", error);
	                alert("데이터 수정 실패");
	            });
	        }
	    });
    </script>
</body>
</html>
