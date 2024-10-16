package Products;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addToCart")
public class insertCart extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 String userId = (String) req.getSession().getAttribute("user_id"); // 세션에서 사용자 ID 가져오기
		 if(userId == null || userId.isEmpty()) {
			 userId = "U001";
		 }
	     String productId = req.getParameter("product_id");
	     try {
	         productDAO dao = new productDAO();
	         dao.addToCart(userId, productId);
	         
	         // JSON 형식으로 응답 반환
	         resp.setContentType("application/json");
	         resp.setStatus(HttpServletResponse.SC_OK);
	         PrintWriter out = resp.getWriter();
	         out.write("{\"message\": \"상품이 카트에 추가되었습니다.\"}");
	         out.flush();
	         
	     } catch (SQLException e) {
	         e.printStackTrace();
	         resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "카트에 추가하는 데 실패했습니다.");
	     } catch (Exception e) {
	         e.printStackTrace();
	         resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "오류가 발생했습니다.");
	     }
	     
	}
}
