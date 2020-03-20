/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Cart;
import javax.ejb.Local;
import util.exception.CartNotFoundException;
import util.exception.CreateNewCartException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Local
public interface CartSessionBeanLocal {

    public Cart createNewCart(Cart cart) throws CreateNewCartException, InputDataValidationException;

    public void deleteCart(Long id) throws CartNotFoundException;
    public Cart getCartById(Long id) throws CartNotFoundException;
    public Cart getCartByCustomerId(Long customerId);
    public void addListingToCart(Long listingId, Long cartId, int quantity) throws CartNotFoundException;
    public void deleteListingFromCart(Long listingId, Long cartId, int quantity) throws CartNotFoundException;
    
}
