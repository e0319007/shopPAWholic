/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BilingDetail;
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
import util.exception.BilingDetailNotFoundException;
import util.exception.CreateNewBilingDetailException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Stateless
@LocalBean
public class BilingDetailSessionBean implements BilingDetailSessionBeanLocal {

     @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public BilingDetailSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    //didnt add bilingdetailexists exception cos different accounts can have the same biling detail
    //unless want to somehow constrain the same user to not have the same biling detail if they have multiple
    @Override //billing detail created while creating advertisement and order.-> managed bean need to do view only
    public BilingDetail createNewBilingDetail(BilingDetail bilingDetail) throws CreateNewBilingDetailException, InputDataValidationException{
        Set<ConstraintViolation<BilingDetail>> constraintViolations;
        constraintViolations = validator.validate(bilingDetail);
        
        if (constraintViolations.isEmpty()) {
            try{
                em.persist(bilingDetail);
                em.flush();
                return bilingDetail;
            } catch (Exception ex) {
                throw new CreateNewBilingDetailException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void updateBilingDetail(BilingDetail bilingDetail) throws InputDataValidationException {
        Set<ConstraintViolation<BilingDetail>> constraintViolations;
        constraintViolations = validator.validate(bilingDetail);
        if (constraintViolations.isEmpty()){
            if (bilingDetail.getBilingDetailId() != null) {
                em.merge(bilingDetail);
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void deleteBilingDetail(Long id) throws BilingDetailNotFoundException {
        BilingDetail bilingDetail = getBilingDetailById(id);
        em.remove(bilingDetail);
    }
    
    
    @Override
    public BilingDetail getBilingDetailById(Long id) throws BilingDetailNotFoundException {
        BilingDetail bilingDetail = em.find(BilingDetail.class, id);
        if (bilingDetail != null) {
            return bilingDetail;
        } else {
            throw new BilingDetailNotFoundException("Biling Detail " + id + " does not exist");
        }
    }
    
    
    /*public List<BilingDetail> retrieveBilingDetailByCustomer(Long customerId) {
        Query query = em.createQuery("SELECT bd FROM BilingDetail bd WHERE bd.customer.id =: customerId");
        query.setParameter("inCustomerId", customerId);
        return query.getResultList();
    //have to call listings.size cos lazy load
    }*/

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<BilingDetail>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
