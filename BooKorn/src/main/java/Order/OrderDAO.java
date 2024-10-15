package Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import Cart.Cart;
import oracle.sql.TIMESTAMP;

public class OrderDAO {

	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "system";
	private String password = "pass";

	// public static void main(String[] args) throws SQLException {
	// getCart("minsoo001");
	// }
	private Connection dbCon() throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// DB 연결 객체 반환
		return con;
	}

	public int createOrder(String userId, String cartId, int totalPrice, String memo, String name, String address, String phone) throws SQLException {
	    Connection con = dbCon();
	    String orderId = generateOrderId(); // 주문 ID 생성 로직
	    String sql = "INSERT INTO orders (order_id, user_id, cart_id, total_price, memo, name, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement pst = con.prepareStatement(sql)) {
	        pst.setString(1, orderId);
	        pst.setString(2, userId);
	        pst.setString(3, cartId);
	        pst.setInt(4, totalPrice);
	        pst.setString(5, memo);
	        pst.setString(6, name);
	        pst.setString(7, address);
	        pst.setString(8, phone);
	        pst.executeUpdate();
	    }

	    return 1;
	}


	public Order getOrder(String user_id) throws SQLException {
		
			Connection con = dbCon();
		
		//sql
			String sql="SELECT * "
					+ "FROM orders "
					+ "WHERE user_id = ?";
			
			
			//sql실행
			PreparedStatement pst=null;
			ResultSet rs =null;
			
			try {
				pst=con.prepareStatement(sql);
				
				pst.setString(1, user_id);  // ? 자리에 userId 값을 바인딩
				
				rs =pst.executeQuery();
				
				String order_id = rs.getString(1);
				String userid = rs.getString(2);
				String cart_id = rs.getString(3);;
				int total_price = rs.getInt(4);
				Timestamp order_date = rs.getTimestamp(5);
				String memo = rs.getString(6);
				Order or = new Order(order_id,userid, cart_id, total_price, order_date, memo);
					
				System.out.println(or.toString());
				
				
				//해제
				pst.close();
				rs.close();
				con.close();
				
				return or;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		return null;
		
	}

	private String generateOrderId() {
	    Random random = new Random();
	    int randomNumber = 100000 + random.nextInt(900000); // 6자리 숫자 생성 (100000 ~ 999999)
	    return "ORD" + randomNumber;
	}

}
