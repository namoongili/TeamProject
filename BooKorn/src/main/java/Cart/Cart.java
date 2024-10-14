package Cart;

public class Cart {

	
	int quantity;
	String product_name;
	String product_detail;
	int product_price;
	String product_id;
	

	public Cart(int quantity, String product_name, String product_detail, int product_price, String product_id) {
		super();
		this.quantity = quantity;
		this.product_name = product_name;
		this.product_detail = product_detail;
		this.product_price = product_price;
		this.product_id = product_id;
	}

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProduct_name() {
		return product_name;
	}


	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}


	public String getProduct_detail() {
		return product_detail;
	}


	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}


	public int getProduct_price() {
		return product_price;
	}


	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

	public String getProduct_id() {
		return product_id;
	}


	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}


	@Override
	public String toString() {
		return "Cart [quantity=" + quantity + ", product_name=" + product_name + ", product_detail=" + product_detail
				+ ", product_price=" + product_price + ", product_id=" + product_id + "]";
	}


	

	
}
