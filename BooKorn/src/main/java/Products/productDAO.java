package Products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import Comments.comment;

public class productDAO {

	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String user="system";
	String password="pass";
	
	
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
	
//	private void close(AutoCloseable ...a) {
//		
//		for(AutoCloseable item : a) {
//			try {
//				item.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	}
	

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
			
			rs.close();
			con.close();
			pst.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	        rs.close();
			con.close();
			pst.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	        
	       
			con.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
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
			con.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
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
			
			con.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
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
	        
	        pstmt.close();
	        conn.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e; // 예외를 다시 던져서 호출자에서 처리
	    } finally {
	        
	    }
	    
	    
	    
	    return cartId; // 생성된 cart_id 반환
	}


	public void addItemToCart(String cartId, String productId) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = dbCon();

	        // 카트 안에 해당 제품이 이미 존재하는지 확인
	        String sqlCheckExistence = "SELECT quantity FROM cartitem WHERE cart_id = ? AND product_id = ?";
	        pstmt = conn.prepareStatement(sqlCheckExistence);
	        pstmt.setString(1, cartId);
	        pstmt.setString(2, productId);
	        rs = pstmt.executeQuery();

	        // 이미 존재하는 경우 수량을 증가시키거나, 새로운 항목을 추가하지 않음
	        if (rs.next()) {
	            // 해당 제품이 이미 카트에 존재함
	            int currentQuantity = rs.getInt("quantity");
	            int newQuantity = currentQuantity + 1; // 수량 증가
	            String sqlUpdateQuantity = "UPDATE cartitem SET quantity = ? WHERE cart_id = ? AND product_id = ?";
	            pstmt = conn.prepareStatement(sqlUpdateQuantity);
	            pstmt.setInt(1, newQuantity);
	            pstmt.setString(2, cartId);
	            pstmt.setString(3, productId);
	            pstmt.executeUpdate();
	        } else {
	            // 해당 제품이 카트에 존재하지 않음
	            String cartitemId = "I" + getNextCartItemId(conn);
	            int quantity = 1;
	            System.out.println(cartitemId);
	            System.out.println(cartId);
	            System.out.println(productId);
	            String sqlInsertCartItem = "INSERT INTO cartitem (cartitem_id, cart_id, product_id, quantity) VALUES (?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(sqlInsertCartItem);
	            pstmt.setString(1, cartitemId);
	            pstmt.setString(2, cartId);
	            pstmt.setString(3, productId);
	            pstmt.setInt(4, quantity);
	            pstmt.executeUpdate();
	            
	            rs.close();
	            pstmt.close();
	            conn.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e; // 예외를 다시 던져서 호출자에서 처리
	    } finally {
	        
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
	        String sqlCheckCart = "SELECT cart_id FROM cart WHERE user_id = 'minsoo001'";
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
	            System.out.println(cartId);
	        }


	        
	        
	        addItemToCart(cartId, productId); // 카트에 아이템 추가
	        
	        rs.close();
	        pstmtCart.close();
	        conn.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e; // 예외를 다시 던져서 호출자에서 처리
	    } finally {
	        
	    }
	}


	private String getNextCartId(Connection conn) throws SQLException {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 5);
//	    String sql = "SELECT cart_seq.NEXTVAL FROM dual";
//	    try (PreparedStatement pstmt = conn.prepareStatement(sql);
//	         ResultSet rs = pstmt.executeQuery()) {
//	        if (rs.next()) {
//	            return rs.getInt(1);
//	        }
//	    }
//	    throw new SQLException("Failed to retrieve next cart ID");
	}

	private String getNextCartItemId(Connection conn) throws SQLException {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 5);
//	    String sql = "SELECT cartitem_seq.NEXTVAL FROM dual";
//	    try (PreparedStatement pstmt = conn.prepareStatement(sql);
//	         ResultSet rs = pstmt.executeQuery()) {
//	        if (rs.next()) {
//	            return rs.getInt(1);
//	        }
//	    }
//	    throw new SQLException("Failed to retrieve next cart item ID");
	}

	
	
//	public static void main(String[] args) throws SQLException {
//		
//		productDAO dao = new productDAO();
//		dao.addToCart("minsoo001", "P001");
//		
//	}
	
}
