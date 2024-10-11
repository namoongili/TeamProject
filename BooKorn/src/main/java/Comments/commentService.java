package Comments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import Products.product;
import Products.productDAO;

public class commentService{
	
	productDAO dao = new productDAO();
	
	public int totalCmt(String prod_id) {
		product pd = dao.selectProduct(prod_id);
		int result = pd.getProduct_commentCnt();
		return result;
	}
	
	public JSONObject pagCmt(String prod_id, int page) {
		ArrayList<comment> list = dao.getCmtListPag(prod_id, page);
		JSONArray arr = new JSONArray();
		for(int i=0;i<list.size();i++) {
			comment c = list.get(i);
			JSONObject o = new JSONObject();
			o.append("user_id", c.getUser_id());
			o.append("comment_text", c.getComment_text());
			o.append("rating", c.getRating());
			o.append("comment_date", c.getComment_date());
			arr.put(o);
		}
		JSONObject result = new JSONObject();
		result.put("list",arr );
		result.put("page", page);
		return result;
	}
	
	
}
