package User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
	
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:testdb";
	String user="scott";
	String password="tiger";
	
	private Connection  dbcon() {
		
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
			
	public String selectUser(String user_id, String password) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql= "select username from users where user_id = ? and password =? ";
		
		String result = "";
		
		try {
			con= dbcon();
			pst = con.prepareStatement(sql);
			pst.setString(1, user_id);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
				
		
	}
	

}
