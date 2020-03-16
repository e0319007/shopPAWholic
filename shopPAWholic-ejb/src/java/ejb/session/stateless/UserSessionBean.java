/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package ejb.session.stateless;

import entity.Cart;
import entity.Customer;
import entity.User;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
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
import util.exception.CreateNewCartException;
import util.exception.DeleteUserException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UsernameExistException;
import util.security.CryptographicHelper;

/**
 *
 * @author yeeqinghew
 */
@Stateless
@LocalBean
public class UserSessionBean implements UserSessionBeanLocal {

   @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    @EJB
    private CartSessionBeanLocal cartSessionBeanLocal;

    public UserSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    public Long createNewUser(User newUser) throws UsernameExistException, UnknownPersistenceException, InputDataValidationException{
        Set<ConstraintViolation<User>>constraintViolations;
        
        try{
            constraintViolations = validator.validate(newUser);
            
            if(constraintViolations.isEmpty()){
                if(newUser instanceof Customer) cartSessionBeanLocal.createNewCart(new Cart());
                em.persist(newUser);
                em.flush();

               
            }
            else{
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            } 
        } catch(PersistenceException ex){
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")){
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")){
                    throw new UsernameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } catch (CreateNewCartException ex) {
           Logger.getLogger(UserSessionBean.class.getName()).log(Level.SEVERE, null, ex);
       }
         return newUser.getUserId();   
    }
    
    
    public List<User> retrieveAllUsers(){
        Query query = em.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }
    
    public User retrieveUserByUserId(Long userId) throws UserNotFoundException{
        User user = em.find(User.class, userId);
        if( user != null) {
            return user;
        }
        else {
            throw new UserNotFoundException("User ID " + userId + " does not exist!");
        }
    }
    
    
    
    public User retrieveUserByUsername(String username) throws UserNotFoundException{
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            return (User)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new UserNotFoundException("User's Username " + username + " does not exist!");
        }
    }
    
    public User userLogin(String username, String password) throws InvalidLoginCredentialException{
        try{
            User user = retrieveUserByUsername(username);            
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + user.getSalt()));
            
            if(user.getPassword().equals(passwordHash)){
                //user.getSaleTransactionEntities().size();                
                return user;
            }
            else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch(UserNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    
    
//    public void deleteUser(Long userId) throws UserNotFoundException, DeleteUserException {
//        User userToRemove = retrieveUserByUserId(userId);
//        if(userToRemove.getSaleTransactionEntities().isEmpty()){
//            em.remove(userToRemove);
//        }
//        else{
//            // New in v4.1 to prevent deleting staff with existing sale transaction(s)
//            throw new DeleteUserException("User ID " + userId + " is associated with existing sale transaction(s) and cannot be deleted!");
//        }
//    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<User>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
