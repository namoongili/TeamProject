package User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class logoutSevlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  HttpSession session = req.getSession(false); // 세션이 없으면 null 반환
	        
	        if (session != null) {
	            // 세션 무효화
	            session.invalidate();
	        }

	        // 로그아웃 후 로그인 페이지로 리다이렉트
	        resp.sendRedirect("/BooKorn/home");
		
	}
}
