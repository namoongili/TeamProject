<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Cart.Cart"%>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");

// 로그인 여부 확인
Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<style>
.list {
	width: 80%;
	border: 1px solid;
}

th {
	font-size: large;
}

td {
	text-align: center;
	border: 1px solid;
}

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

.sum {
    width: 80%;
	justify-content: end;
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
		<table class="list">
			<thead>
				<tr>
					<th>책이름</th>
					<th>내용</th>
					<th>가격</th>
					<th>수량</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<%
				ArrayList<Cart> cl = (ArrayList<Cart>) request.getAttribute("cartItemsResult");
				for (int i = 0; i < cl.size(); i++) {
					String product_name = cl.get(i).getProduct_name();
					String product_detail = cl.get(i).getProduct_detail();
					int product_price = cl.get(i).getProduct_price();
					int quantity = cl.get(i).getQuantity();
					String product_id = cl.get(i).getProduct_id();
				%>
				<tr data-product-id="<%=product_id%>">
					<!-- product_id가 올바르게 설정되었는지 확인 -->
					<td><%=product_name%></td>
					<td><%=product_detail%></td>
					<td class="price"><%=product_price%></td>
					<td><input class="quantity" type="text" value="<%=quantity%>"></td>
					<td><button class="delete-btn">삭제</button></td>
				</tr>

				<%
				}
				%>
			</tbody>


		</table>
		<div class="sum">
			개수: <span id="sum"></span> 총가격: <span id="totalPrice"></span>
		</div>
		<button onclick="sendOrder()" class="orderbtn">주문하기</button>

	</section>

	<footer> </footer>

</body>
<script>
    let sumElement = document.getElementById("sum");
    let totalPriceElement = document.getElementById("totalPrice");
    let quantityInputs = document.querySelectorAll(".quantity");
    let priceElements = document.querySelectorAll(".price");

    function calculateSum() {
        // 업데이트된 요소를 가져옵니다.
        quantityInputs = document.querySelectorAll(".quantity");
        priceElements = document.querySelectorAll(".price");
        
        let sum = 0;
        let totalPrice = 0;
    
        quantityInputs.forEach((input, index) => {
            const quantity = parseFloat(input.value);
            const price = parseFloat(priceElements[index].textContent);
    
            if (!isNaN(quantity) && !isNaN(price)) {
                sum += quantity;
                totalPrice += quantity * price;
            }
        });
    
        sumElement.textContent = sum;
        totalPriceElement.textContent = totalPrice.toLocaleString(); // 숫자를 천단위 콤마로 포맷팅
    }
    
    
    function sendOrder() {
        const orderDetails = {
            userId: 'minsoo001', // 현재 사용자 ID
            cartItems: [] // 장바구니 아이템을 담을 배열
        };
    
        // 각 아이템의 productId와 quantity를 orderDetails에 추가
        $(".quantity").each(function() {
            const quantity = parseInt($(this).val(), 10);
            const productId = $(this).closest('tr').data('product-id');
    
            if (!isNaN(quantity) && quantity > 0) {
                orderDetails.cartItems.push({
                    productId: productId,
                    quantity: quantity
                });
            }
        });
    
        console.log("주문 상세 정보:", orderDetails); // 전송 전 확인을 위한 로그
    
        // AJAX 요청으로 주문 정보 전송
        $.ajax({
            type: "POST",
            url: "/BooKorn/cart",
            contentType: "application/json",
            data: JSON.stringify(orderDetails),
            success: function(response) {
                window.location.href = "/BooKorn/order"; // 주문 완료 후 이동할 URL
            },
            error: function(err) {
                alert("주문 요청에 실패했습니다.");
                console.error("Error:", err);
            }
        });
    }
    
    



    
        
    

    $(".delete-btn").on("click", function () {
        const row = $(this).closest("tr");
        const cartItemId = row.data("product-id");
    
        $.ajax({
            type: "post",
            url: "/BooKorn/cart/delete", // CartDeleteServlet URL
            data: { cartItemId: cartItemId },
            success: function (data) {
                if (data.status === "success") {
                    console.log("삭제 성공:", data);
                    row.remove(); // 해당 tr 제거
                    calculateSum(); // 총합 계산
                    console.log("총합 계산 함수 호출됨"); // 추가 로그
                } else {
                    alert("삭제에 실패했습니다: " + data.message);
                }
            },
            error: function (err) {
                alert("요청에 실패했습니다");
                console.log("Error:", err);
            }
        });
    });
    
    // 초기 합계 계산 및 이벤트 리스너 등록
    function registerInputListeners() {
        quantityInputs.forEach((input) => {
            input.addEventListener("input", calculateSum);
        });
    }
    
    // 페이지 로딩 시 초기 합계 계산 및 이벤트 리스너 등록
    window.addEventListener('load', function() {
        calculateSum();
        registerInputListeners();
    });
    
    


    


</script>
</html>