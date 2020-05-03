package ejb.session.stateless;

import entity.Seller;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.SellerNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Stateless
@Local(SellerSessionBeanLocal.class)
public class SellerSessionBean implements SellerSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public SellerSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override
    public Long createNewSeller(Seller newSeller) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Seller>> constraintViolations = validator.validate(newSeller);
            if (constraintViolations.isEmpty()) {
                em.persist(newSeller);
                em.flush();
                return newSeller.getUserId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new UserUsernameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    @Override
    public void updateSeller(Seller seller) throws SellerNotFoundException, InputDataValidationException {
        if(seller != null && seller.getUserId() !=null) {
            Set<ConstraintViolation<Seller>>constraintViolations = validator.validate(seller); 
            if(constraintViolations.isEmpty()) {
                Seller sellerToUpdate = retrieveSellerById(seller.getUserId()); 
                if(sellerToUpdate.getEmail().equals(seller.getEmail())) {
                    sellerToUpdate.setName(seller.getName());
                    sellerToUpdate.setContactNumber(seller.getContactNumber());
                    sellerToUpdate.setPassword(seller.getPassword());
                } else {
                    throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            } else {
                throw new SellerNotFoundException("Seller ID not provided for seller to be updated");
            }
        }
    }
    
    @Override 
    public void updateVerification(Seller seller) throws SellerNotFoundException, InputDataValidationException {
        if(seller != null && seller.getUserId() !=null) {
            Set<ConstraintViolation<Seller>>constraintViolations = validator.validate(seller); 
            if(constraintViolations.isEmpty()) {
                Seller sellerToUpdate = retrieveSellerById(seller.getUserId()); 
                if(sellerToUpdate.getEmail().equals(seller.getEmail())) {
                    sellerToUpdate.setVerification(seller.getVerification());
                } else {
                    throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            } else {
                throw new SellerNotFoundException("Veirification ID not provided for seller to be updated");
            }
        }
    }
    
    @Override
    public void updateVerifiedAdmin(Seller sellerToView) {
        Seller seller = em.find(Seller.class, sellerToView.getUserId());
        seller.setVerified(true);
        em.persist(seller);
    }

    @Override
    public Seller retrieveSellerByUsername(String username) throws SellerNotFoundException {
        Query query = em.createQuery("SELECT s FROM Seller s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);
        try {
            return (Seller)query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new SellerNotFoundException("Seller Username " + username + " does not exist!");
        }
    }
    @Override
    public Seller retrieveSellerById(Long sellerId) throws SellerNotFoundException {
        Seller seller = em.find(Seller.class, sellerId);
        if(seller != null) {
            return seller;
        } else {
            throw new SellerNotFoundException("Seller ID " + sellerId + " does not exist!");
        }
    }
    
    @Override
    public List<Seller> retrieveAllSellers(){
        Query query = em.createQuery("SELECT s FROM Seller s");
        return query.getResultList();
    }
    
    @Override
    public Map<String, Integer> retrieveTotalNumberOfSellersForTheYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Query query;
        Map<String, Integer> userPerMonth = new HashMap<>();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        for (int i = 0; i < months.size(); i++) {
            query = em.createQuery("SELECT s FROM Seller s WHERE EXTRACT(YEAR(s.accCreatedDate)) = :inYear AND EXTRACT(MONTH(s.accCreatedDate)) = :inMonth ");
            query.setParameter("inYear", year);
            query.setParameter("inMonth", i+1);
            userPerMonth.put(months.get(i), (query.getResultList()).size());
        }
        return userPerMonth;
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Seller>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
