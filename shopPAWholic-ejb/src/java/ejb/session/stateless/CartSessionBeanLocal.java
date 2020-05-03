package ejb.session.stateless;

import entity.Cart;
import javax.ejb.Local;
import util.exception.CartNotFoundException;
import util.exception.CreateNewCartException;
import util.exception.InputDataValidationException;

@Local
public interface CartSessionBeanLocal {

    public Cart createNewCart(Cart cart) throws CreateNewCartException, InputDataValidationException;

    public void deleteCart(Long id) throws CartNotFoundException;
    public Cart getCartById(Long id) throws CartNotFoundException;
    public Cart getCartByCustomerId(Long customerId);
    public void addListingToCart(Long listingId, Long cartId, int quantity) throws CartNotFoundException;
    public void clearCart(Long cartId) throws CartNotFoundException;
    public void updateCart(Cart cart);
    
}
