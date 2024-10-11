package Comments;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Products.productService;

@WebServlet("/comments")
public class commentServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int current_page = 1;
		String page =  req.getParameter("page");
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		String prod_id = req.getParameter("prod_id");
		
		//System.out.println(prod_id);
		
		
		commentService service = new commentService();
		JSONObject  result = service.pagCmt(prod_id, current_page);
		int total_page = service.totalCmt(prod_id);
		result.put("totalPages", total_page );
		//System.out.println(result);
		
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().println( result);
		
	}
}
