<!-- load-wifi.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Open API 와이파이 정보 가져오기</title>
<!-- jQuery CDN 제거 -->
<style>
/* 로딩 스피너 스타일 */
.loading-spinner {
	border: 16px solid #f3f3f3; /* 회색 */
	border-top: 16px solid #3498db; /* 파랑 */
	border-radius: 50%;
	width: 120px;
	height: 120px;
	animation: spin 2s linear infinite;
	margin: 0 auto;
	margin-top: 50px; /* 페이지 중앙에 배치 */
	display: none; /* 초기에는 숨김 */
}

/* 스피너 회전 애니메이션 */
@
keyframes spin { 0% {
	transform: rotate(0deg);
}
100


%
{
transform


:


rotate
(


360deg


)
;


}
}
</style>
<script>
        document.addEventListener("DOMContentLoaded", function() {
            // 페이지 로드 시 로딩 스피너 표시
            document.querySelector(".loading-spinner").style.display = "block";

            // Fetch API를 사용하여 서블릿에 요청 전송
            fetch("WifiInfoSyncServlet")
                .then(response => {
                    if (!response.ok) {
                        throw new Error("서버에서 데이터를 가져오는 데 실패했습니다.");
                    }
                    return response.text();
                })
                .then(data => {
                    // 서버로부터 데이터를 성공적으로 받으면 스피너 숨김
                    document.querySelector(".loading-spinner").style.display = "none";
                    // 받은 데이터로 메시지 표시
                    document.getElementById("message").textContent = data;
                })
                .catch(error => {
                    // 에러 발생 시 스피너 숨김
                    document.querySelector(".loading-spinner").style.display = "none";
                    // 에러 메시지 표시
                    document.getElementById("message").textContent = error.message;
                });
        });
    </script>
</head>
<body>
	<div style="text-align: center;">
		<!-- 메시지 -->
		<h1 id="message">WIFI 정보를 가져오고 있습니다.</h1>
		<p>
			<a href="index.jsp">홈으로 돌아가기</a>
		</p>
		<!-- 로딩 스피너 -->
		<div class="loading-spinner"></div>
	</div>
</body>
</html>
