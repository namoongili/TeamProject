package Products;

public class product {

	String product_id;
	String category_id;
	String product_name;
	String product_author;
	String product_company;
	int product_price;
	String product_detail;
	int product_commentCnt;
 	double product_grade;
	String product_image;
	
	
	public product(String product_id, String category_id, String product_name, String product_author,
			String product_company, int product_price, String product_detail, int product_commentCnt,
			double product_grade, String product_image) {
		super();
		this.product_id = product_id;
		this.category_id = category_id;
		this.product_name = product_name;
		this.product_author = product_author;
		this.product_company = product_company;
		this.product_price = product_price;
		this.product_detail = product_detail;
		this.product_commentCnt = product_commentCnt;
		this.product_grade = product_grade;
		this.product_image = product_image;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_author() {
		return product_author;
	}
	public void setProduct_author(String product_author) {
		this.product_author = product_author;
	}
	public String getProduct_company() {
		return product_company;
	}
	public void setProduct_company(String product_company) {
		this.product_company = product_company;
	}
	public int getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	public String getProduct_detail() {
		return product_detail;
	}
	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}
	public int getProduct_commentCnt() {
		return product_commentCnt;
	}
	public void setProduct_commentCnt(int product_commentCnt) {
		this.product_commentCnt = product_commentCnt;
	}
	public double getProduct_grade() {
		return product_grade;
	}
	public void setProduct_grade(double product_grade) {
		this.product_grade = product_grade;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	@Override
	public String toString() {
		return "product [product_id=" + product_id + ", category_id=" + category_id + ", product_name=" + product_name
				+ ", product_author=" + product_author + ", product_company=" + product_company + ", product_price="
				+ product_price + ", product_detail=" + product_detail + ", product_commentCnt=" + product_commentCnt
				+ ", product_grade=" + product_grade + ", product_image=" + product_image + "]";
	}
	
	
	
}
