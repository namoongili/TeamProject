<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Cart.Cart"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<table>
    <tr>
        <th>Title</th>
        <th>Quantity</th>
    </tr>
    <% 
    ArrayList<Cart> cl = (ArrayList<Cart>) request.getAttribute("cartItemsResult");
        for (int i = 0; i<cl.size(); i++) { 
            String product_id = cl.get(i).getProduct_id();
            int quantity = cl.get(i).getQuantity();
    %>
        <tr>
            <td><%= product_id %></td>
            <td><%= quantity %></td>
        </tr>
    <% } %>
</table>


</body>
</html>