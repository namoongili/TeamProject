package User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user")
public class userServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String user_id = req.getParameter("username");
		String password = req.getParameter("password");

		userDAO dao = new userDAO();
		String userName = dao.selectUser(user_id, password);
		if (!userName.isEmpty()) {
			HttpSession session = req.getSession();

			session.setAttribute("userId", user_id);

			session.setAttribute("userName", userName);
			resp.sendRedirect("/BooKorn/home");
		} else {
			resp.sendRedirect("login.jsp?error=1");
		}

	}

}
