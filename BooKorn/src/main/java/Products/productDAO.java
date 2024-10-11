package Products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.eclipse.jdt.internal.compiler.batch.Main;

import Comments.comment;

public class productDAO {

	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:testdb";
	String user="scott";
	String password="tiger";
	
	
	private Connection dbCon() {
		
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
	
	private void close(AutoCloseable ...a) {
		
		for(AutoCloseable item : a) {
			try {
				item.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public product selectProduct(String product_id) {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "select * from products where product_id = ? ";
		
		product p = null ;
		try {
			con = dbCon();
			pst = con.prepareStatement(sql);
			pst.setString(1, product_id);
			rs= pst.executeQuery();
			
			if (rs.next()) {
			    	String pid = rs.getString(1);
					String cid = rs.getString(2);
					String pname = rs.getString(3);
					String pauthor = rs.getString(4);
					String pcompany	= rs.getString(5);
					int pprice = rs.getInt(6);
					String pdetail = rs.getString(7);
					int pcomcnt = rs.getInt(8);
					double pgrade = rs.getDouble(9);
					String pimage = rs.getString(10);
					
					p = new product(pid, cid, pname, pauthor, pcompany, pprice, pdetail, pcomcnt, pgrade, pimage);
			} 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs,pst,con);
		}
		return p ;
	}
	
	
	
	public ArrayList<comment> selectComments(String product_id) {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "select * from comments where product_id = ? ";
		
		ArrayList<comment> list = new  ArrayList<>();
		
		try {
			con = dbCon();
			pst = con.prepareStatement(sql);
			pst.setString(1, product_id);
			rs= pst.executeQuery();
			
			while(rs.next()) {
				String cid = rs.getString(1);
				String pid = rs.getString(2);
				String uid = rs.getString(3);
				String ctext = rs.getString(4);
				int rating = rs.getInt(5);
				
				Timestamp tdate = rs.getTimestamp(6);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = sdf.format(tdate);
				
				comment comm = new comment(cid, pid, uid, ctext,rating ,formattedDate);
				list.add(comm);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		close(rs,pst,con);
		
		return list ;
	}
	
	public ArrayList<comment> getCmtListPag(String prod_id , int page){
		 int start = (page-1) * 5 +1;
		 int end = page *5;
		 
		 Connection con =null;
		 PreparedStatement pst = null;
		 ResultSet rs = null;
		 String sql = "SELECT * FROM (\r\n"
		 		+ "    SELECT ROWNUM num, comment_id, product_id, user_id, comment_text, rating, comment_date \r\n"
		 		+ "    FROM (\r\n"
		 		+ "        SELECT comment_id, product_id, user_id, comment_text, rating, comment_date \r\n"
		 		+ "        FROM comments \r\n"
		 		+ "        WHERE product_id = ?\r\n"
		 		+ "    )\r\n"
		 		+ ") \r\n"
		 		+ "WHERE num BETWEEN ? AND ? ";

		 
		 ArrayList<comment> list = new  ArrayList<>();
		 
		 con = dbCon();
		 try {
			pst = con.prepareStatement(sql);
			pst.setString(1, prod_id);
			pst.setInt(2, start);
			pst.setInt(3, end);
			rs=pst.executeQuery();
			while(rs.next()) {
				String cid = rs.getString(2);
				String pid = rs.getString(3);
				String uid = rs.getString(4);
				String ctext = rs.getString(5);
				int rating = rs.getInt(6);
				
				Timestamp tdate = rs.getTimestamp(7);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = sdf.format(tdate);
				
				comment comm = new comment(cid, pid, uid, ctext,rating ,formattedDate);
				list.add(comm);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 close(rs,pst,con);
		
		return list ;
	}
	
	
	
	/*
	public static void main(String[] args) {
		productDAO dao = new productDAO();
		System.out.println(dao.selectProduct("P001"));
		System.out.println(dao.selectComments("P001"));
		System.out.println(dao.getCmtListPag("P001", 1));
		
	}
	 */
}
