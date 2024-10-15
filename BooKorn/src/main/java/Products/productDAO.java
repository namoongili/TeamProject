package Products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		
		product p = null;
		
		try {
			con = dbCon();
			pst = con.prepareStatement(sql);
			pst.setString(1, product_id);
			rs= pst.executeQuery();
			if(rs.next()) {
				String pid = rs.getString(1);
				String cid = rs.getString(2);
				String pname = rs.getString(3);
				String pauthor = rs.getString(4);
				String pcompany = rs.getString(5);
				int pprice = rs.getInt(6);
				String pdtl = rs.getString(7);
				int cmtcnt = rs.getInt(8);
				double pgrade = rs.getDouble(9);
				String pimage = rs.getString(10);
				
				p = new product(pid, cid, pname, pauthor, pcompany, pprice, pdtl, cmtcnt, pgrade, pimage);
				
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(rs,pst,con);
		return p;
	}
	
	
	
	public ArrayList<comment> selectComments(String product_id , int currnet_page) {
		
		int start = (currnet_page-1)*5+1;
		int end = (currnet_page)*5;
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM ("
                + "  SELECT rownum AS num, comment_id, user_id, comment_text, rating, comment_date, product_id"
                + "  FROM comments WHERE product_id = ?"
                + ") WHERE num BETWEEN ? AND ?";
		
		ArrayList<comment> list = new  ArrayList<>();
		
		try {
			con = dbCon();
			pst = con.prepareStatement(sql);
			pst.setString(1, product_id);
			pst.setInt(2, start);
	        pst.setInt(3, end);
	        
	        rs = pst.executeQuery();
	        
	        // ResultSet에서 데이터 읽기
	        while (rs.next()) {
	            // 첫 번째 생성자 사용
	            comment cmt = new comment(
	                rs.getString("comment_id"),     // 댓글 ID
	                rs.getString("product_id"),     // 제품 ID
	                rs.getString("user_id"),        // 사용자 ID
	                rs.getString("comment_text"),   // 댓글 내용
	                rs.getInt("rating"),            // 평점
	                rs.getTimestamp("comment_date") // 작성 날짜
	            );
	            list.add(cmt);
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(rs,pst,con);
		return list ;
	}
	
	public int insertComment(comment newComment) {
		
		Connection con = null;
	    PreparedStatement pst = null;

	    String sql = "INSERT INTO comments (product_id, user_id, comment_text, rating) VALUES (?, ?, ?, ?)";
	    int rRow = -1;
	    try {
	    	con = dbCon();
			pst = con.prepareStatement(sql);
			pst.setString(1, newComment.getProduct_id());
	        pst.setString(2, newComment.getUser_id());
	        pst.setString(3, newComment.getComment_text());
	        pst.setInt(4, newComment.getRating());
	        rRow = pst.executeUpdate();
	        updateCount(newComment.getProduct_id());
	        updateRate(newComment.getProduct_id());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    close(pst, con);
	    
		return rRow;
	}
	
	public int updateCount(String product_id) {
		Connection con = null;
		PreparedStatement pst = null;
		int rRow =-1;
		String sql = "UPDATE products SET product_commentCnt = product_commentCnt + 1 WHERE product_id = ?";
		
		try {
			con = dbCon();
			pst = con.prepareStatement(sql);
			pst.setString(1, product_id);
			rRow = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(pst, con);
		
		return rRow;
	}
	
	public int updateRate(String product_id) {
		Connection con = null;
		PreparedStatement pst = null;
		int rRow =-1;
		String sqlUpdateGrade = "UPDATE products SET product_grade = (SELECT AVG(rating) FROM comments WHERE product_id = ?) WHERE product_id = ?";
		
		try {
			con = dbCon();
			pst = con.prepareStatement(sqlUpdateGrade);
			pst.setString(1, product_id);
			pst.setString(2, product_id);
			rRow = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(pst, con);
		
		return rRow;
	}
	
	public String createCart(String userId) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String cartId = null;
	    int rRow = 0;
	    System.out.println(userId);
	    try {
	        conn = dbCon();

	        // 시퀀스를 사용하여 cart_id 생성
	        cartId = "c" + getNextCartId(conn);

	        String sqlInsertCart = "INSERT INTO cart (cart_id, user_id) VALUES (?, ?)";
	        pstmt = conn.prepareStatement(sqlInsertCart);
	        pstmt.setString(1, cartId);
	        pstmt.setString(2, userId);
	        rRow = pstmt.executeUpdate();
	        
	        System.out.println(rRow);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e; // 예외를 다시 던져서 호출자에서 처리
	    } finally {
	        close(pstmt, conn); // 자원 정리
	    }
	    
	    
	    
	    return cartId; // 생성된 cart_id 반환
	}


	public void addItemToCart(String cartId, String productId) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = dbCon();

	        String cartitemId = "I" + getNextCartItemId(conn);
	        int quantity = 1;
	        System.out.println(cartitemId);
	        System.out.println(cartId);
	        System.out.println(productId);
	        String sqlInsertCartItem = "INSERT INTO cartitem (cartitem_id, cart_id, product_id, quantity) VALUES ( ? , ?,  ? , ?)";
	        pstmt = conn.prepareStatement(sqlInsertCartItem);
	        pstmt.setString(1, cartitemId);
	        pstmt.setString(2, cartId);
	        pstmt.setString(3, productId);
	        pstmt.setInt(4, quantity);
	        pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e; // 예외를 다시 던져서 호출자에서 처리
	    } finally {
	        close(pstmt, conn); // 자원 정리
	    }
	}


	public void addToCart(String userId, String productId) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmtCart = null;
	    ResultSet rs = null;

	    String cartId = null;
	    System.out.println(userId);
	    System.out.println(productId);
	    
	    try {
	        conn = dbCon();

	        // 카트 존재 여부 확인
	        String sqlCheckCart = "SELECT cart_id FROM cart WHERE user_id = 'U001'";
	        pstmtCart = conn.prepareStatement(sqlCheckCart);
	      //  pstmtCart.setString(1, userId);
	        rs = pstmtCart.executeQuery();

	        // 카트가 없으면 새로 생성
	        
	        if (!rs.next()) {
	        	System.out.println("생성");
	            cartId = createCart(userId); // 카트 생성
	        } else {
	        	
	        	System.out.println("생성ㅀㅀㄹ");
	            cartId = rs.getString("cart_id"); // 기존 카트 ID 가져오기
	        }


	        
	        
	        addItemToCart(cartId, productId); // 카트에 아이템 추가

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e; // 예외를 다시 던져서 호출자에서 처리
	    } finally {
	        close(rs, pstmtCart, conn); // 자원 정리
	    }
	}


	private int getNextCartId(Connection conn) throws SQLException {
	    String sql = "SELECT cart_seq.NEXTVAL FROM dual";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    }
	    throw new SQLException("Failed to retrieve next cart ID");
	}

	private int getNextCartItemId(Connection conn) throws SQLException {
	    String sql = "SELECT cartitem_seq.NEXTVAL FROM dual";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    }
	    throw new SQLException("Failed to retrieve next cart item ID");
	}

	
	/*
	public static void main(String[] args) throws SQLException {
		
		productDAO dao = new productDAO();
		dao.addToCart("U001", "P001");
		
	}
	*/
}
