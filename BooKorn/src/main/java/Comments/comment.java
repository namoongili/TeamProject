package Comments;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class comment {

	String comment_id;
	String product_id;
	String user_id;
	String comment_text;
	int rating;
	Timestamp comment_date;
	String formatDate;
	String filtered_userid ;
	public comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public comment(String comment_id, String product_id, String user_id, String comment_text, int rating,
			Timestamp comment_date) {
		super();
		this.comment_id = comment_id;
		this.product_id = product_id;
		this.user_id = user_id;
		this.comment_text = comment_text;
		this.rating = rating;
		this.comment_date = comment_date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.formatDate= sdf.format(comment_date);
		StringBuilder maskedId = new StringBuilder();
	    maskedId.append(user_id.charAt(0)); 
	    for (int i = 1; i < user_id.length(); i++) {
	        maskedId.append('*'); 
	    }
		this.filtered_userid = maskedId.toString();
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
	    return this.user_id;
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
	    if (comment_date == null) {
	        return ""; 
	    }
	    java.util.Date date = new java.util.Date(comment_date.getTime());
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.format(date);
	}
	 
	public void setComment_date(Timestamp comment_date) {
		this.comment_date = comment_date;
	}
	
	
	
	
	
}
