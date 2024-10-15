package Order;

import java.sql.Timestamp;

import oracle.sql.TIMESTAMP;

public class Order {
	public String order_id; 
    public String user_id;
    public String cart_id;
    public int total_price;
    public Timestamp order_date;
    public String memo;
    
	public Order(String order_id, String user_id, String cart_id, int total_price, Timestamp order_date, String memo) {
		super();
		this.order_id = order_id;
		this.user_id = user_id;
		this.cart_id = cart_id;
		this.total_price = total_price;
		this.order_date = order_date;
		this.memo = memo;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCart_id() {
		return cart_id;
	}
	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public Timestamp getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", user_id=" + user_id + ", cart_id=" + cart_id + ", total_price="
				+ total_price + ", order_date=" + order_date + ", memo=" + memo + "]";
	}
    
    
}
