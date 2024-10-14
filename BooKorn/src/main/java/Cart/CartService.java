package Cart;

import java.sql.SQLException;
import java.util.ArrayList;

public class CartService {
	private CartDAO cartdao;

    public CartService() {
        this.cartdao = new CartDAO();
    }

    
    public ArrayList<Cart> getCart(String user_id) throws SQLException {
        
        return cartdao.getCart(user_id);
    }
    
//    public void deleteCartItem(String userId, String cartItemId) throws SQLException {
//        Connection con = dbCon();
//        String sql = "DELETE FROM cartitem WHERE cart_id = (SELECT cart_id FROM cart WHERE user_id = ?) AND product_id = ?";
//        
//        try (PreparedStatement pst = con.prepareStatement(sql)) {
//            pst.setString(1, userId);
//            pst.setString(2, cartItemId);
//            pst.executeUpdate();
//        } finally {
//            con.close();
//        }
//    }


}
