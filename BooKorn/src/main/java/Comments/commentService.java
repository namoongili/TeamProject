package Comments;

import java.util.ArrayList;

import Products.product;
import Products.productDAO;

public class commentService {

	productDAO dao = new productDAO();
	
	public int getTotCmt(String prod_id) {
		product p = dao.selectProduct(prod_id);
		int totcmt = p.getProduct_commentCnt();
		return totcmt;
	}
	
	public ArrayList<comment> commentpag(String prod_id, int current_page) {
		ArrayList<comment> list = dao.selectComments(prod_id, current_page);
		return list;
	}
	
}
