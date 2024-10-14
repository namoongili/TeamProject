package Comments;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/comments")
public class commentServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String prod_id = req.getParameter("prod_id");
		if(prod_id==null) {
			prod_id="P001";
		}
		int current_page =1;
		String page= req.getParameter("page");
		if(page!=null) {
			current_page =Integer.parseInt(page);
		}
		commentService service = new commentService();
		int totalComments = service.getTotCmt(prod_id);
		int totalPages = (totalComments % 5 == 0) ? (totalComments / 5) : (totalComments / 5) + 1;
		ArrayList <comment> comments = service.commentpag(prod_id, current_page);
		String  a =comments.get(0).getComment_date();
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("currentPage", page);
        jsonResponse.addProperty("totalPages", totalPages);
        jsonResponse.add("comments", new Gson().toJsonTree(comments));
		
        resp.getWriter().write(new Gson().toJson(jsonResponse));
	}
}

