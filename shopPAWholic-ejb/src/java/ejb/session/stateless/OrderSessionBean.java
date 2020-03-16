/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OrderEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewOrderException;
import util.exception.InputDataValidationException;
import util.exception.OrderNotFoundException;

/**
 *
 * @author EileenLeong
 */
@Stateless
public class OrderSessionBean implements OrderSessionBeanLocal {
// order has customer, deliveryDetails, listings, billingdetail
    @EJB(name = "DeliveryDetailSessionBeanLocal")
    private DeliveryDetailSessionBean deliveryDetailSessionBeanLocal;

    @EJB(name = "BilingDetailSessionBeanLocal")
    private BilingDetailSessionBean bilingDetailSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    //@EJB(name = "CustomerSessionBeanLocal")
    //private CustomerSessionBean customerSessionBeanLocal;
    
    
    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public OrderSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    @Override
    public OrderEntity createNewOrder (OrderEntity newOrder) throws CreateNewOrderException, InputDataValidationException{
        Set<ConstraintViolation<OrderEntity>> constraintViolations;
        constraintViolations = validator.validate(newOrder);
        
        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newOrder);
                em.flush();
                
                return newOrder;
            } catch (Exception ex) {
                throw new CreateNewOrderException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
                
    }
    
    @Override
    public OrderEntity getOrderById(Long orderId) throws OrderNotFoundException {
        OrderEntity order = em.find(OrderEntity.class, orderId);
        if (order != null) {
            return order;
        } else {
            throw new OrderNotFoundException("Order Id " + orderId + " does not exist!");
        }
    }
    
    @Override
    public List<OrderEntity> retrieveAllOrders() {
        Query query = em.createQuery("SELECT o FROM Order o ORDER BY o.orderId");
        List<OrderEntity> orders = query.getResultList();
        
        return orders;
    }
   

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<OrderEntity>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
    
