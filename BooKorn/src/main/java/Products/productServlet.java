package Products;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class productServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String prod_id = req.getParameter("id");
		if(prod_id == null) {
			prod_id = "P001";
		}
		productService service = new productService();
		product prd = service.selectPrd(prod_id);
		
		
		req.setAttribute("prod", prd);
		req.getRequestDispatcher("WEB-INF/views/Product.jsp").forward(req, resp);
	}
	
}
