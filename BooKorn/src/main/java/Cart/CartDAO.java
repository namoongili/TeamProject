package Cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDAO {
	
	private String url="jdbc:oracle:thin:@localhost:1521:xe";
	private String user="system";
	private String password="pass";

	public static void main(String[] args) throws SQLException {
		
	}
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
			String sql="SELECT ci.* FROM cart c JOIN cartitem ci ON c.cart_id = ci.cart_id WHERE c.user_id = ?";	
			
			//sql실행
			PreparedStatement pst=null;
			ResultSet rs =null;
			
			ArrayList<Cart> list = new ArrayList<Cart>();
			try {
				pst=con.prepareStatement(sql);
				
				pst.setString(1, user_id);  // ? 자리에 userId 값을 바인딩
				
				rs =pst.executeQuery();
				
				System.out.println(rs.next());
				while( rs.next()) {
					String product = rs.getString(3);
					int quantity = rs.getInt(4);
					
					Cart cart = new Cart(product,quantity);
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
	
	
}
