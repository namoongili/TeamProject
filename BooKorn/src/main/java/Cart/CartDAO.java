package Cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CartDAO {

	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "system";
	private String password = "pass";


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

	public ArrayList<Cart> getCart(String user_id) throws SQLException {
		Connection con = dbCon();

		// sql
		String sql = "SELECT ci.*, p.product_name, p.product_detail, p.product_price\n" + "FROM cart c\n"
				+ "JOIN cartitem ci ON c.cart_id = ci.cart_id\n" + "JOIN products p ON ci.product_id = p.product_id\n"
				+ "WHERE c.user_id = ?";

		// sql실행
		PreparedStatement pst = null;
		ResultSet rs = null;

		ArrayList<Cart> list = new ArrayList<Cart>();
		try {
			pst = con.prepareStatement(sql);

			pst.setString(1, user_id); // ? 자리에 userId 값을 바인딩

			rs = pst.executeQuery();

			while (rs.next()) {
				String id = rs.getString(2);
				String p_id = rs.getString(3);
				int quantity = rs.getInt(4);
				String name = rs.getString(5);
				String detail = rs.getString(6);
				int price = rs.getInt(7);
				Cart cart = new Cart(id, quantity, name, detail, price, p_id);

				System.out.println(cart.toString());
				list.add(cart);

			}

			// 해제
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

	public void saveCartItems(String userId, ArrayList<Cart> cartItems) throws SQLException {
        Connection con = dbCon();
        String cartId = generateCartId();

        try {
            // 기존 cart와 cartitem 삭제
            String deleteCartSql = "DELETE FROM cart WHERE user_id = ?";
            String deleteCartItemsSql = "DELETE FROM cartitem WHERE cart_id IN (SELECT cart_id FROM cart WHERE user_id = ?)";

            try (PreparedStatement pst1 = con.prepareStatement(deleteCartItemsSql);
                 PreparedStatement pst2 = con.prepareStatement(deleteCartSql)) {
                pst1.setString(1, userId);
                pst1.executeUpdate();

                pst2.setString(1, userId);
                pst2.executeUpdate();
            }

            // 새로운 cart 추가
            String insertCartSql = "INSERT INTO cart (cart_id, user_id) VALUES (?, ?)";
            try (PreparedStatement pst = con.prepareStatement(insertCartSql)) {
                pst.setString(1, cartId);
                pst.setString(2, userId);
                pst.executeUpdate();
            }

            // cartItems 리스트를 순회하며 cartitem 테이블에 데이터 추가
            String insertCartItemSql = "INSERT INTO cartitem (cartitem_id, cart_id, product_id, quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(insertCartItemSql)) {
                for (Cart cartItem : cartItems) {
                    String cartItemId = UUID.randomUUID().toString().replace("-", "").substring(0, 10); // cartitem_id 생성

                    pst.setString(1, cartItemId);
                    pst.setString(2, cartId);
                    pst.setString(3, cartItem.getProduct_id());
                    pst.setInt(4, cartItem.getQuantity());

                    pst.addBatch(); // Batch 처리
                }
                pst.executeBatch(); // 한 번에 실행
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            con.close();
        }
    }


	public int getTotalPrice(String userId) throws SQLException {
		Connection con = dbCon();

		// sql
		String sql = "SELECT ci.*, p.product_name, p.product_detail, p.product_price\n" + "FROM cart c\n"
				+ "JOIN cartitem ci ON c.cart_id = ci.cart_id\n" + "JOIN products p ON ci.product_id = p.product_id\n"
				+ "WHERE c.user_id = ?";

		// sql실행
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		int sum = 0;

		try {
			pst = con.prepareStatement(sql);

			pst.setString(1, userId); // ? 자리에 userId 값을 바인딩

			rs = pst.executeQuery();

			while (rs.next()) {
				int quantity = rs.getInt(4);
				int price = rs.getInt(7);
				
				sum += quantity * price;

			}

			// 해제
			pst.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sum;
	}
	
	// cart_id를 생성하는 메서드
    public String generateCartId() {
        // UUID를 사용하여 고유한 ID를 생성한 후, 필요한 길이로 잘라서 반환합니다.
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

	public String getCartId(String userId) throws SQLException {
		Connection con = dbCon();

		// sql
		String sql = "select cart.cart_id from cart where cart.user_id = ?";

		// sql실행
		PreparedStatement pst = null;
		ResultSet rs = null;

		String id = null;
		
		try {
			pst = con.prepareStatement(sql);

			pst.setString(1, userId); // ? 자리에 userId 값을 바인딩

			rs = pst.executeQuery();
			
			while (rs.next()) {
				id = rs.getString(1);

			}

			

			// 해제
			pst.close();
			rs.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;
	}

}
