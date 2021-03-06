/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DeliveryDetail;
import entity.OrderEntity;
import java.util.List;
import javax.persistence.Query;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewDeliveryDetailException;
import util.exception.CustomerNotFoundException;
import util.exception.DeliveryDetailNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Stateless
@LocalBean
public class DeliveryDetailSessionBean implements DeliveryDetailSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public DeliveryDetailSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    
    @Override
    public DeliveryDetail createNewDeliveryDetail(DeliveryDetail deliveryDetail) throws CreateNewDeliveryDetailException, InputDataValidationException {
        Set<ConstraintViolation<DeliveryDetail>>constraintViolations = validator.validate(deliveryDetail);
        
        if (constraintViolations.isEmpty()) {
            try {
                em.persist(deliveryDetail);
                em.flush();
                return deliveryDetail;
            } catch (Exception ex) {
                throw new CreateNewDeliveryDetailException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
             throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    //add something about changing enum of delivery detail under the update delivery detail
    @Override
    public void updateDeliveryDetail(DeliveryDetail deliveryDetail) throws InputDataValidationException {
        Set<ConstraintViolation<DeliveryDetail>>constraintViolations = validator.validate(deliveryDetail);
        if (constraintViolations.isEmpty()) {
            if (deliveryDetail.getDeliveryDetailId() != null) {
                em.merge(deliveryDetail);
            } 
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    
    @Override
    public void deleteDeliveryDetail(Long id) throws DeliveryDetailNotFoundException {
        DeliveryDetail deliveryDetail = retrieveDeliveryDetailById(id);
        em.remove(deliveryDetail);
    }
    
    @Override
    public DeliveryDetail retrieveDeliveryDetailById(Long id) throws DeliveryDetailNotFoundException {
        try {
            DeliveryDetail deliveryDetail = em.find(DeliveryDetail.class, id);
            return deliveryDetail;
        } catch (Exception ex) {
            throw new DeliveryDetailNotFoundException("Delivery Detail ID " + id + " does not exist!");
        }
    }
    
   
    @Override
    public DeliveryDetail retrieveDeliveryDetailByOrderId (long orderId) throws DeliveryDetailNotFoundException{
        try {
            OrderEntity order = em.find(OrderEntity.class, orderId);
            Query q = em.createQuery("SELECT detail FROM DeliveryDetail detail WHERE detail.deliveryDetailId = :inDeliveryId");
            q.setParameter("inDeliveryId", order.getDeliveryDetail().getDeliveryDetailId());
            return (DeliveryDetail) q.getSingleResult();
        } catch (Exception ex) {
            throw new DeliveryDetailNotFoundException("Delivery Detail ID " + " does not exist!");
        }
    }
    
    @Override
    public void addDeliveryStatus(String status, long deliveryStatusId) {
        DeliveryDetail deliveryDetail = em.find(DeliveryDetail.class, deliveryStatusId);
        deliveryDetail.getStatusLists().add(status);
        em.merge(deliveryDetail);
    }
    
    //also retrieve delivery details by customer
    // 
//    public List<DeliveryDetail> retrieveDeliveryDetailByCustomer(Long customerId) throws DeliveryDetailNotFoundException, CustomerNotFoundException {
//        Customer customer = em.find(Customer.class, customerId);
//        if (customer != null) {
//            customer.getDeliveryDetails();
//              the rest of the logic
//        }
//    }
    

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<DeliveryDetail>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
    
}
