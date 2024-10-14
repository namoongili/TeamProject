package Cart;

public class Cart {

	int quantity;
	String product_name;
	String product_detail;
	int product_price;
	

	public Cart(int quantity, String product_name, String product_detail , int product_price) {
		super();
		this.quantity = quantity;
		this.product_name = product_name;
		this.product_detail = product_detail;
		this.product_price = product_price;
		
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


	@Override
	public String toString() {
		return "Cart [quantity=" + quantity + ", product_name=" + product_name + ", product_detail=" + product_detail
				+ ", product_price=" + product_price + "]";
	}

	
}
