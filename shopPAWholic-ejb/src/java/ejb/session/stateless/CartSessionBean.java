/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Cart;
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
    
    @Override
    public void updateCart(Cart cart) throws InputDataValidationException{
        Set<ConstraintViolation<Cart>>constraintViolations = validator.validate(cart);
        if(constraintViolations.isEmpty()) {
            if(cart.getCartId()!= null) {
                em.merge(cart);
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    //also add listing to cart???
    
    @Override
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
    
    //retrieve according to date added & grouped by name of the seller 
    /*public Cart retrieveCartByCustomer(Long customerId) {
        Query query = em.createQuery("SELECT c FROM Cart c WHERE c.customer.id:=customerId");
        query.setParameter("inCustomerId", customerId);
        return (Cart)query.getSingleResult();
    }*/
    

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Cart>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
