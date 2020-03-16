/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Advertisement;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.AdvertisementNotFoundException;
import util.exception.CreateNewAdvertisementException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Stateless
public class AdvertisementSessionBean implements AdvertisementSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public AdvertisementSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override
    public Advertisement createNewAdvertisement(Advertisement advertisement) throws CreateNewAdvertisementException, InputDataValidationException {
        Set<ConstraintViolation<Advertisement>> constraintViolations;
        constraintViolations = validator.validate(advertisement);
        
        if (constraintViolations.isEmpty()) {
            try {
               em.persist(advertisement);
               em.flush();
               
               return advertisement;
            } catch (Exception ex) {
                throw new CreateNewAdvertisementException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void updateAdvertisement(Advertisement advertisement) throws InputDataValidationException {
        Set<ConstraintViolation<Advertisement>>constraintViolations = validator.validate(advertisement);
        if(constraintViolations.isEmpty()) {
            if(advertisement.getAdvertisementId() != null) {
                em.merge(advertisement);
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void deleteAdvertisement(Long advertisementId) throws AdvertisementNotFoundException {
        Advertisement advertisement = retrieveAdvertisementById(advertisementId);
        em.remove(advertisement);
    }
    
    @Override
    public Advertisement retrieveAdvertisementById(Long id) throws AdvertisementNotFoundException {
        Advertisement advertisement = em.find(Advertisement.class, id);
        if (advertisement != null) {
            return advertisement;
        } else {
            throw new AdvertisementNotFoundException("Advertisement ID of " + id + " does not exist!");
        }
    }
    
    @Override
    public List<Advertisement> retrieveAllAdvertisements() {
        Query query = em.createQuery("SELECT a FROM Advertisement a ORDER BY a.startDate");
        List<Advertisement> advertisements = query.getResultList();
        return advertisements;
    }
            
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Advertisement>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}