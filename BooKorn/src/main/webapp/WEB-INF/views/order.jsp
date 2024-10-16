<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Order.OrderService"%>
<%@ page import="Cart.Cart"%>
<%@ page import="java.util.ArrayList"%>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");

// 로그인 여부 확인
Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Summary</title>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0 auto;
	padding: 0;
	box-sizing: border-box;
}

/* 헤더 스타일 */
header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 15px;
	background-color: #fff;
	border-bottom: 1px solid #e0e0e0;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
	width: 98.5%;
	overflow: hidden;
}

/* 내비게이션 메뉴 */
.nav-bar {
	display: flex;
	gap: 10px;
}

.nav-bar a {
	text-decoration: none;
	color: #333;
	font-weight: bold;
	padding: 8px 12px;
	transition: background-color 0.3s, color 0.3s;
	border-radius: 5px;
}

.nav-bar a:hover {
	background-color: #f0f0f0;
	color: #007BFF;
}

/* 검색 바 */
.search-bar {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%; /* 전체 너비를 차지하도록 설정 */
	max-width: 300px; /* 검색 바의 최대 너비 */
	margin: 0 auto; /* 중앙에 위치하도록 설정 */
}

.search-bar input {
	flex-grow: 1;
	max-width: 200px; /* 검색 바의 최대 너비 */
	padding: 10px;
	border-radius: 20px;
}

.search-bar button {
	background-color: #007BFF;
	color: white;
	border: none;
	padding: 10px 15px;
	border-radius: 20px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.search-bar button:hover {
	background-color: #0056b3;
}

/* 유저 메뉴 */
.user-menu {
	display: flex;
	align-items: center;
	gap: 10px;
	margin-left: auto; /* 유저 메뉴를 오른쪽 끝으로 보내기 위한 설정 */
}

.user-menu a {
	text-decoration: none;
	color: #333;
	font-weight: bold;
	transition: color 0.3s;
}

.user-menu a:hover {
	color: #007BFF;
}

/* 메인 컨텐츠 */
.main-content {
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	padding: 20px;
	width: 100%;
	max-width: 1600px;
	gap: 20px;
	align-items: center;
}

.orderbtn {
	width: 200px;
	background-color: #007BFF;
	color: white;
	border: none;
	padding: 10px 15px;
	border-radius: 20px;
	cursor: pointer;
	transition: background-color 0.3s;
	align-self: center;
}

/* 푸터 스타일 */
footer {
	background-color: #333;
	color: white;
	text-align: center;
	padding: 10px;
	position: fixed;
	width: 100%;
	bottom: 0;
}
</style>
<body>


	<header>
		<!-- 네비게이션 바 -->
		<div class="nav-bar">
			<a href="?category=DOMESTIC">국내도서</a> <a href="?category=FOREIGN">외국도서</a>
			<a href="?category=USED">중고도서</a> <a href="?category=EBOOK">eBook</a>
			<a href="?category=CAT005">크레마클럽</a> <a href="?category=CD/LP">CD/LP</a>
		</div>

		<!-- 검색 바 -->
		<div class="search-bar">
			<input type="text" placeholder="예비중학 필수! 1등 초등 세계사">
			<button>검색</button>
		</div>

		<!-- 유저 메뉴 -->
		<div class="user-menu">
			<%
			if (isLoggedIn != null && isLoggedIn) {
			%>
			<a href="#">마이페이지</a> <a href="/BooKorn/cart">장바구니</a> <a
				href="/logout">로그아웃</a>
			<%
			} else {
			%>
			<a href="/login">로그인</a>
			<%
			}
			%>
		</div>
	</header>
	<section class="main-content">
		<div>
			이름: <input type="text" id="name">
		</div>
		<div>
			주소: <input type="text" id="address">
		</div>
		<div>
			전화번호: <input type="text" id="phone">
		</div>
		<div>
			요구사항: <input type="text" id="memo">
		</div>
		<div>
			주문내역:
			<ul>
				<%
				ArrayList<Cart> cartItems = (ArrayList<Cart>) session.getAttribute("cartItems");
				if (cartItems != null) {
					for (int i = 0; i < cartItems.size(); i++) {
						int sum = +cartItems.get(i).getProduct_price() * cartItems.get(i).getQuantity();
				%>
				<li><%=cartItems.get(i).getProduct_name()%> - <%=cartItems.get(i).getQuantity()%>개</li>
				<%
				}
				} else {
				%>
				<li>장바구니가 비어 있습니다.</li>
				<%
				}
				%>
			</ul>
		</div>
		<div class="sum">
			총 가격: <span id="totalPrice"><%=(int) request.getAttribute("totalPrice")%></span>
		</div>
		<button onclick="submitOrder()" class="orderbtn">주문완료</button>
	</section>
	<footer> </footer>

	<script>
        function submitOrder() {
            const orderData = {
                userId: '<%=session.getAttribute("userId")%>', // 세션에서 가져온 사용자 ID
                cartId: '<%=session.getAttribute("cartId")%>',
				// 세션에서 가져온 장바구니 ID
				totalPrice : parseInt(
						document.getElementById("totalPrice").innerText, 10),
				memo : document.getElementById("memo").value,
				name : document.getElementById("name").value,
				address : document.getElementById("address").value,
				phone : document.getElementById("phone").value
			};

			$.ajax({
				type : "POST",
				url : "/BooKorn/order",
				contentType : "application/json",
				data : JSON.stringify(orderData),
				success : function(response) {
					alert("주문이 완료되었습니다!");
					window.location.href = "/BooKorn/home";
				},
				error : function(err) {
					alert("주문 요청에 실패했습니다.");
					console.error("Error:", err);
				}
			});
		}
	</script>
</body>
</html>
