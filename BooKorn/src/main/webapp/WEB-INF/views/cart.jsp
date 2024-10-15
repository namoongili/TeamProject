<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Cart.Cart"%>
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
</style>

<body>

	<div class="main">
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
		<div>
			개수: <span id="sum"></span> 총가격: <span id="totalPrice"></span>
		</div>
		<button onclick="sendOrder()">주문하기</button>

	</div>

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

        // 삭제 확인 문구
        if (confirm("정말 이 상품을 삭제하시겠습니까?")) {
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
        } else {
            console.log("사용자가 삭제를 취소했습니다.");
        }
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