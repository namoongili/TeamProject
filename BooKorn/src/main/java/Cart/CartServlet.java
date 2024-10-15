package Cart;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	String userId;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		userId = (String) session.getAttribute("userId");
		System.out.println(userId);
		//데이터베이스에 접근, 유저 ID를 검색해서 해당 데이터를 찾아서 해당 유저에 저장된 장바구니를 호출
		try {
			System.out.println("getCart전");
			ArrayList<Cart> cl = getCart(userId);
			System.out.println("getCart후");
			request.setAttribute("cartItemsResult", cl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//가져온 장바구니의 내용을 속성저장하고 뷰에 전달
		

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 세션에서 사용자 ID 가져오기
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("userId");

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
	    JsonArray jsonCartItems = jsonObject.getAsJsonArray("cartItems");

	    // ArrayList<Cart>로 변환
	    ArrayList<Cart> cartItems = new ArrayList<>();
	    for (JsonElement element : jsonCartItems) {
	        JsonObject itemObj = element.getAsJsonObject();
	        String productId = itemObj.get("productId").getAsString();
	        int quantity = itemObj.get("quantity").getAsInt();

	        // Cart 객체 생성 및 리스트에 추가
	        Cart cartItem = new Cart(productId,quantity);
	        cartItems.add(cartItem);
	    }

	    // CartService를 이용하여 장바구니 저장
	    CartService cs = new CartService();
	    try {
	        cs.saveCartItems(userId, cartItems);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        return;
	    }

	    // 성공 응답
	    response.setStatus(HttpServletResponse.SC_OK);
	}

	
	
	
	private ArrayList<Cart> getCart(String userId) throws SQLException {
		CartService cs = new CartService();
        return cs.getCart(userId);
    }
}
