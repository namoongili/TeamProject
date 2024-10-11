package Comments;

import java.text.DateFormat;

public class comment {

	String comment_id;
	String product_id;
	String user_id;
	String comment_text;
	int rating;
	String comment_date;
	
	
	public comment(String comment_id, String product_id, String user_id, String comment_text, int rating,
			String comment_date) {
		super();
		this.comment_id = comment_id;
		this.product_id = product_id;
		this.user_id = user_id;
		this.comment_text = comment_text;
		this.rating = rating;
		this.comment_date = comment_date;
	}
	public comment(String product_id, String user_id, String comment_text, int rating) {
		super();
		this.product_id = product_id;
		this.user_id = user_id;
		this.comment_text = comment_text;
		this.rating = rating;
	}
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getComment_text() {
		return comment_text;
	}
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}
	
	public String getFilteredUserId() {
        if (user_id == null || user_id.length() < 2) {
            return user_id; 
        }
        return user_id.charAt(0) + "***"; 
    }
	
	
	
	
}
