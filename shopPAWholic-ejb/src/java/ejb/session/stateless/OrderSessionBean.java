/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BillingDetail;
import entity.Customer;
import entity.DeliveryDetail;
import entity.Listing;
import entity.OrderEntity;
import entity.Seller;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.OrderStatus;
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
    private DeliveryDetailSessionBeanLocal deliveryDetailSessionBeanLocal;

    @EJB(name = "BillingDetailSessionBeanLocal")
    private BillingDetailSessionBeanLocal billingDetailSessionBeanLocal;

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
    
    @Override // new billing detail initialised in this method, delivery detail created at customer side and bind it in this method by passing id in.
    public OrderEntity createNewOrder (OrderEntity newOrder, Long DeliveryDetailId, String ccNum, Long customerId, List<Listing> listings, Long sellerId) throws CreateNewOrderException, InputDataValidationException{
        Set<ConstraintViolation<OrderEntity>> constraintViolations;
        constraintViolations = validator.validate(newOrder);
        
        if (constraintViolations.isEmpty()) {
            try {
                System.out.println("Entering bean");
                Date date = new Date(System.currentTimeMillis());
                BillingDetail billingDetail = new BillingDetail(ccNum, date);
                billingDetailSessionBeanLocal.createNewBillingDetail(billingDetail);
                
                Seller seller = em.find(Seller.class, sellerId);
                Customer customer = em.find(Customer.class, customerId);
                DeliveryDetail deliveryDetail = em.find(DeliveryDetail.class, DeliveryDetailId);
              
                seller.getOrders().add(newOrder);
                customer.getOrders().add(newOrder);
                newOrder.setCustomer(customer);
                newOrder.setSeller(seller);
                newOrder.setDeliveryDetail(deliveryDetail);
                
                billingDetail.setCustomer(customer);
                newOrder.setBillingDetail(billingDetail);
                billingDetail.setOrder(newOrder);
                customer.getBillingDetails().add(billingDetail);
      
//                for(Listing l:listings) {
//                    em.persist(listings);
//                }
//              
                for (Listing l : listings) {
                    l.setQuantityOnHand(l.getQuantityOnHand() - 1);
                }
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
        Query query = em.createQuery("SELECT o FROM OrderEntity o ORDER BY o.orderId");
        List<OrderEntity> orders = query.getResultList();
        
        return orders;
    }
   
    @Override
    public List<OrderEntity> retrieveOrderByCustomerId(Long customerId) {
        Query q = em.createQuery("SELECT o FROM OrderEntity o WHERE o.customer.userId = :inUserId");
        q.setParameter("inUserId", customerId);
        
        return q.getResultList();
    }
    
    @Override
    public List<OrderEntity> retrieveOrderBySellerId(Long sellerId) {
        Query q = em.createQuery("SELECT o FROM OrderEntity o WHERE o.seller.userId = :inSellerId");
        q.setParameter("inSellerId", sellerId);

        return q.getResultList();
    }
    
    @Override
    public String changeOrderStatus(OrderStatus os, Long orderId) {
        try {
            OrderEntity order = getOrderById(orderId);
            order.setOrderStatus(os);
            
        } catch (OrderNotFoundException ex) {
            Logger.getLogger(OrderSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(os.equals(OrderStatus.PAID)) return "Order is Paid";
            else if(os.equals(OrderStatus.REFUND)) return "Order is Refunded";
            else if(os.equals(OrderStatus.LOST)) return "Order is Lost";
            else if(os.equals(OrderStatus.SHIPPED)) return "Order is Shipped";
            else return "Order is Completed";
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
    
