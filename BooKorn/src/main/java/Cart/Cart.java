package Cart;

import java.util.List;
import java.util.Map;

public class Cart {
	
	String product_id;
	int quantity;
	
	public Cart(String product_id, int quantity) {
		super();
		this.product_id = product_id;
		this.quantity = quantity;
	}
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Cart [product_id=" + product_id + ", quantity=" + quantity + "]";
	}
}
