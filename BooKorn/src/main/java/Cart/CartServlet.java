package Cart;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	String userId;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("userId", userId="U001");
		//데이터베이스에 접근, 유저 ID를 검색해서 해당 데이터를 찾아서 해당 유저에 저장된 장바구니를 호출
		try {
			ArrayList<Cart> cl = getCart(userId);
			
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
        
		//현재 화면에 있는 장바구니 정보를 바탕으로 데이터베이스에 전송

		
		

        request.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(request, response);
    }
	
	
	
	private ArrayList<Cart> getCart(String userId) throws SQLException {
		CartService cs = new CartService();
        return cs.getCart(userId);
    }
}
