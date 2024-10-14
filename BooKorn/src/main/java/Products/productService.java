package Products;

import java.util.ArrayList;

import Comments.comment;

public class productService {
	productDAO dao = new productDAO();
	public product selectPrd(String product_id) {
		product prd = null;
		prd = dao.selectProduct(product_id);
		return prd;
	}
	
	public ArrayList<comment> selectComm(String product_id){
		ArrayList<comment> list = new ArrayList<>();
		//list = dao.selectComments(product_id);
		return list;
	}
	
	
	
	
	
}
