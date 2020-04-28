/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Cart;
import entity.Listing;
import java.math.BigDecimal;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CartNotFoundException;
import util.exception.CreateNewCartException;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;

/**
 *
 * @author Joanna Ng
 */
@Stateless
@LocalBean
public class CartSessionBean implements CartSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CartSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    @Override
    public Cart createNewCart(Cart cart) throws CreateNewCartException, InputDataValidationException {
        Set<ConstraintViolation<Cart>> constraintViolations;
        constraintViolations = validator.validate(cart);
        
        if (constraintViolations.isEmpty()) {
            try {
               em.persist(cart);
               em.flush();
               
               return cart;
            } catch (Exception ex) {
                throw new CreateNewCartException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override //dont need to call
    public void deleteCart(Long id) throws CartNotFoundException {
        Cart cart = getCartById(id);
        em.remove(cart);
    }
    
    @Override
    public Cart getCartById(Long id) throws CartNotFoundException {
        Cart cart = em.find(Cart.class, id);
        if (cart != null) {
            return cart;
        } else {
            throw new CartNotFoundException("Cart ID " + id + " does not exist");
        }
    }
    
    @Override
    public Cart getCartByCustomerId(Long customerId) {
        Query q = em.createQuery("SELECT c FROM Cart c WHERE c.customer.userId = :inCustomerId");
        Cart cart = (Cart) q.getSingleResult();
        cart.getListings().size();
        return cart;
        
    }
    
    @Override
    public void addListingToCart(Long listingId, Long cartId, int quantity) throws CartNotFoundException {
        Listing listing = em.find(Listing.class, listingId);
        Cart cart = getCartById(cartId);
        for(int i = 0; i < quantity; i++)
            cart.getListings().add(listing);
    }
    
    @Override
    public void clearCart(Long cartId) throws CartNotFoundException {
        System.out.println("Clear cart");
        Cart cart = getCartById(cartId);
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.setTotalQuantity(0);
        cart.getListings().clear();
    }

    //retrieve according to date added & grouped by name of the seller 
    /*public Cart retrieveCartByCustomer(Long customerId) {
        Query query = em.createQuery("SELECT c FROM Cart c WHERE c.customer.id:=customerId");
        query.setParameter("inCustomerId", customerId);
        return (Cart)query.getSingleResult();
    }*/
    
    @Override
    public void updateCart(Cart cart) {
        System.out.println("Cart ID passed in: " + cart.getCartId());
        
        Cart cartPersisted = em.find(Cart.class, cart.getCartId());
        cartPersisted.setListings(cart.getListings());
        cartPersisted.setTotalPrice(cart.getTotalPrice());
        cartPersisted.setTotalQuantity(cart.getTotalQuantity());
    }
    

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Cart>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
