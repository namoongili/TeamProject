package Cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

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
    
    public int getTotalPrice(String userId) throws SQLException {
    	return cartdao.getTotalPrice(userId);
    }
    
    public void saveCartItems(String userId, ArrayList<Cart> cartItems) throws SQLException {
    	cartdao.saveCartItems(userId, cartItems);
    }


	public String getCartId(String userId) throws SQLException {
		
		return cartdao.getCartId(userId);
	}

    
}
