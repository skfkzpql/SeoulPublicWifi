<!-- bookmark-group-add.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>북마크 그룹 추가</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1>북마크 그룹 추가</h1>
    <%@ include file="header.jsp"%>

    <form id="addBookmarkGroupForm" action="insertBookmarkGroupServlet" method="post" accept-charset="UTF-8">
        <table>
            <tr>
                <th>북마크 이름</th>
                <td><input type="text" id="groupName" name="groupName"></td>
            </tr>
            <tr>
                <th>순서</th>
                <td><input type="number" id="sequence" name="sequence"></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center"><button type="submit">추가</button></td>
            </tr>
        </table>
    </form>

    <script>
	    document.getElementById("addBookmarkGroupForm").addEventListener("submit", function(event) {
	        event.preventDefault();
	
	        var groupName = document.getElementById("groupName").value.trim();
	        var sequence = document.getElementById("sequence").value.trim();
	
	        if (groupName === "" || sequence === "") {
	            showMessage("북마크 이름과 순서를 모두 입력해주세요.");
	        } else {
	            var bookmarkGroupDTO = {
	            	group_name: groupName,
	                sequence: sequence
	            };
	
	            fetch("AddBookmarkGroupServlet", {
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
	                    alert("데이터 추가 실패 (동일한 순서를 가진 데이터가 이미 존재합니다)");
	                } else {
	                    alert("데이터 삽입 실패");
	                }
	            })
	            .catch(error => {
	                console.error("북마크 그룹 삭제 싪 :", error);
	                showMessage("북마크 그룹 추가에 실패했습니다. 다시 시도해주세요.");
	            });
	        }
	    });
	
	    // 메시지 표시 함수
	    function showMessage(message) {
	        document.getElementById("message").textContent = message;
	    }
    </script>
</body>
</html>
