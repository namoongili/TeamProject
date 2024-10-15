package Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Cart.Cart;
import Cart.CartService;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	String userId;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    session.setAttribute("userId", userId="minsoo001");
	    ArrayList<Cart> cartItems = (ArrayList<Cart>) session.getAttribute("cartItemsResult");
	    int totalPrice = 0;
	    
	    if (cartItems == null) {
	        // 세션에 cartItems가 없는 경우 빈 리스트를 설정하거나 기본 동작을 지정합니다.
	    	CartService cs = new CartService();
	        try {
				cartItems = cs.getCart(userId);
				session.setAttribute("cartId", cs.getCartId(userId));
				System.out.println(cartItems);
				totalPrice = cs.getTotalPrice(userId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        request.setAttribute("totalPrice", totalPrice);
	        session.setAttribute("cartItems", cartItems);
	    }

	    request.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(request, response);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        // JSON 데이터 파싱
	        BufferedReader reader = request.getReader();
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	        String jsonData = sb.toString();

	        // JSON 데이터 파싱
	        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
	        String userId = jsonObject.get("userId").getAsString();
	        String cartId = jsonObject.get("cartId").getAsString();
	        int totalPrice = jsonObject.get("totalPrice").getAsInt();
	        String memo = jsonObject.get("memo").getAsString();
	        String name = jsonObject.get("name").getAsString();
	        String address = jsonObject.get("address").getAsString();
	        String phone = jsonObject.get("phone").getAsString();

	        // 주문 저장 로직 호출
	        OrderService orderService = new OrderService();
	        int result = orderService.createOrder(userId, cartId, totalPrice, memo, name, address, phone);

	        if (result > 0) {
	            response.setStatus(HttpServletResponse.SC_OK);
	        } else {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}







	private Order getOrder(String user_id) throws SQLException {
		OrderService os = new OrderService();
        return os.getOrder(user_id);
    }

}


