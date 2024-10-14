<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Cart.Cart"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
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
				%>
				<tr>
					<td><%=product_name%></td>
					<td><%=product_detail%></td>
					<td class="price"><%=product_price%></td>
					<td><input class="quantity" type="text" value="<%=quantity%>"></td>
					<td><button onClick="">삭제</button></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		<div>
			개수: <span id="sum"></span> 총가격: <span id="totalPrice"></span>
		</div>
		<button>주문하기</button>
	</div>

</body>
<script>
    let sumElement = document.getElementById("sum");
    let totalPriceElement = document.getElementById("totalPrice");
    let quantityInputs = document.querySelectorAll(".quantity");
    let priceElements = document.querySelectorAll(".price");

    // 합계 및 총 가격 계산 함수 정의
    function calculateSum() {
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

    // 모든 quantity input에 이벤트 리스너 추가
    quantityInputs.forEach((input) => {
        input.addEventListener("input", calculateSum);
    });

    // 페이지 로딩 시 초기 합계 계산
    window.addEventListener('load', calculateSum);


    $.ajax( 
	{
		type: "get",			 
		url: "/ajaxPaging/foodPage"  ,	
		data:{page: page},		
		success: function( data){
			 			
			console.log(data);	
			//
			let list = data.list;
			
			let str="<ul>";
			for(let i=0; i< list.length; i++){
				let item  = list[i];
				str+= "<li>"+ item.name +  item.price + "</li>"
			}
			str+= "</ul>";
			
			
			 $("#result").html( str );				 
			 $("#page").val( data.page );
			 
		},
		error: function(err ){	
			 //
			 alert("요청에 실패했습니다");
			 console.log( err);
		}
	 }
</script>
</html>