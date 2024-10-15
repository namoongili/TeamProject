package Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OrderService {
	private OrderDAO ordao;
	
	public OrderService(){
		this.ordao = new OrderDAO();
	}
    
	public int createOrder(String userId, String cartId, int totalPrice, String memo, String name, String address, String phone) throws SQLException {
		return ordao.createOrder(userId, cartId, totalPrice, memo, name, address, phone);
	}
	public Order getOrder(String user_id) throws SQLException {
		return ordao.getOrder(user_id);
	}
}
