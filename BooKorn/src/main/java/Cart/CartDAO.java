package Cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDAO {
	
	private String url="jdbc:oracle:thin:@localhost:1521:testdb";
	private String user="scott";
	private String password="tiger";

//	public static void main(String[] args) throws SQLException {
//		getCart("minsoo001");
//	}
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
	
	
	public ArrayList<Cart> getCart(String user_id) throws SQLException{
		Connection con = dbCon();
		
		//sql
			String sql="SELECT ci.*, p.product_name, p.product_detail, p.product_price\n"
					+ "FROM cart c\n"
					+ "JOIN cartitem ci ON c.cart_id = ci.cart_id\n"
					+ "JOIN products p ON ci.product_id = p.product_id\n"
					+ "WHERE c.user_id = ?";
			
			
			//sql실행
			PreparedStatement pst=null;
			ResultSet rs =null;
			
			ArrayList<Cart> list = new ArrayList<Cart>();
			try {
				pst=con.prepareStatement(sql);
				
				pst.setString(1, user_id);  // ? 자리에 userId 값을 바인딩
				
				rs =pst.executeQuery();
				
//				String product = rs.getString(3);
//				int quantity = rs.getInt(4);
//				Cart cart = new Cart(product,quantity);
//			
//				list.add(cart);
				
				while( rs.next()) {
					String id = rs.getString(3);
					int quantity = rs.getInt(4);
					String name = rs.getString(5);
					String detail = rs.getString(6);
					int price = rs.getInt(7);
					Cart cart = new Cart(quantity,name,detail,price,id);
					
					System.out.println(cart.toString());
					list.add(cart);
					
				}
				
				//해제
				pst.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

		return list;		
	}
	
	public void deleteCartitem(String userId, String cartItemId) throws SQLException {
		Connection con = dbCon();
	
        String sql = "DELETE FROM cartitem WHERE cart_id = (SELECT cart_id FROM cart WHERE user_id = ?) AND product_id = ?";
        
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, userId);
            pst.setString(2, cartItemId);
            pst.executeUpdate();
        } finally {
            con.close();
        }
	}
	
	
}
