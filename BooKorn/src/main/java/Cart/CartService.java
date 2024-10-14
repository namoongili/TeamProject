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
    
    public void deleteCartItem(String userId, String cartItemId) throws SQLException {
        cartdao.deleteCartitem(userId, cartItemId);
    }


}
