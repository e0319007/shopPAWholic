/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BillingDetail;
import java.util.List;
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
import util.exception.BillingDetailNotFoundException;
import util.exception.CreateNewBillingDetailException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Stateless
@LocalBean
public class BillingDetailSessionBean implements BillingDetailSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public BillingDetailSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    //didnt add billingdetailexists exception cos different accounts can have the same billing detail
    //unless want to somehow constrain the same user to not have the same billing detail if they have multiple
    @Override //billing detail created while creating advertisement and order.-> managed bean need to do view only
    public BillingDetail createNewBillingDetail(BillingDetail billingDetail) throws CreateNewBillingDetailException, InputDataValidationException{
        Set<ConstraintViolation<BillingDetail>> constraintViolations;
        constraintViolations = validator.validate(billingDetail);
        
        if (constraintViolations.isEmpty()) {
            try{
                em.persist(billingDetail);
                em.flush();
                return billingDetail;
            } catch (Exception ex) {
                throw new CreateNewBillingDetailException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void updateBillingDetail(BillingDetail billingDetail) throws InputDataValidationException {
        Set<ConstraintViolation<BillingDetail>> constraintViolations;
        constraintViolations = validator.validate(billingDetail);
        if (constraintViolations.isEmpty()){
            if (billingDetail.getBillingDetailId() != null) {
                em.merge(billingDetail);
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void deleteBillingDetail(Long id) throws BillingDetailNotFoundException {
        BillingDetail billingDetail = getBillingDetailById(id);
        em.remove(billingDetail);
    }
    
    
    @Override
    public BillingDetail getBillingDetailById(Long id) throws BillingDetailNotFoundException {
        BillingDetail billingDetail = em.find(BillingDetail.class, id);
        if (billingDetail != null) {
            return billingDetail;
        } else {
            throw new BillingDetailNotFoundException("Billing Detail " + id + " does not exist");
        }
    }
    
    
    public List<BillingDetail> retrieveBillingDetailByCustomer(Long customerId) {
        Query query = em.createQuery("SELECT bd FROM BillingDetail bd WHERE bd.customer.userId =: customerId");
        query.setParameter("inCustomerId", customerId);
        
        List<BillingDetail> billingDetails = query.getResultList();
        return billingDetails;
    //have to call listings.size cos lazy load
    }
    
    public List<BillingDetail> retrieveBillingDetailBySeller(Long sellerId) {
        Query query = em.createQuery("SELECT bd FROM BillingDetail bd WHERE bd.seller.userId = :inSellerId");
        query.setParameter("inSellerId", sellerId);
        
        List<BillingDetail> billingDetails = query.getResultList();
        return billingDetails;
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<BillingDetail>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
