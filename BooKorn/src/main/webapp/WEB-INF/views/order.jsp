<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Order.OrderService"%>
<%@ page import="Cart.Cart"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Summary</title>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<body>
    이름: <input type="text"	id="name">
    주소: <input type="text" id="address">
    전화번호: <input type="text" id="phone">
    요구사항: <input type="text" id="memo">
    주문내역:
    <ul>
        <%
        ArrayList<Cart> cartItems = (ArrayList<Cart>) session.getAttribute("cartItems");
        if (cartItems != null) {
            for (int i = 0; i < cartItems.size(); i++) {
            	int sum =+ cartItems.get(i).getProduct_price() * cartItems.get(i).getQuantity();
        %>
            <li><%= cartItems.get(i).getProduct_name() %> - <%= cartItems.get(i).getQuantity() %>개</li>
        <%
            }
        } else {
        %>
            <li>장바구니가 비어 있습니다.</li>
        <%
        }
        %>
    </ul>
    
    총 가격: <span id="totalPrice"><%= (int)request.getAttribute("totalPrice") %></span>
    <button onclick="submitOrder()">주문완료</button>

    <script>
        function submitOrder() {
            const orderData = {
                userId: '<%= session.getAttribute("userId") %>', // 세션에서 가져온 사용자 ID
                cartId: '<%= session.getAttribute("cartId") %>', // 세션에서 가져온 장바구니 ID
                totalPrice: parseInt(document.getElementById("totalPrice").innerText, 10),
                memo: document.getElementById("memo").value,
                name: document.getElementById("name").value,
                address: document.getElementById("address").value,
                phone: document.getElementById("phone").value
            };

            $.ajax({
                type: "POST",
                url: "/BooKorn/order",
                contentType: "application/json",
                data: JSON.stringify(orderData),
                success: function(response) {
                    alert("주문이 완료되었습니다!");
                   window.location.href = "/BooKorn/home";
                },
                error: function(err) {
                    alert("주문 요청에 실패했습니다.");
                    console.error("Error:", err);
                }
            });
        }
    </script>
</body>
</html>
