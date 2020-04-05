package ejb.session.stateless;

import entity.Cart;
import entity.Customer;
import entity.User;
import java.util.List;
import java.util.Set;
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
import util.exception.DeleteUserException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;
import util.security.CryptographicHelper;

/**
 *
 * @author yeeqinghew
 */
@Stateless
@Local(UserSessionBeanLocal.class)
public class UserSessionBean implements UserSessionBeanLocal {

   @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
   
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public UserSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    //user login
   @Override
    public User userLogin(String email, String password) throws InvalidLoginCredentialException{
        try{
            User user = retrieveUserByEmail(email);            
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
    
    //create new user 
   @Override
    public Long createNewUser(User newUser) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<User>>constraintViolations = validator.validate(newUser);
            if(constraintViolations.isEmpty()) {
                em.persist(newUser);
                if (newUser instanceof Customer) {
                    Cart cart = new Cart();
                    
                    ((Customer) newUser).setCart(cart);
                    cart.setCustomer((Customer) newUser);
                    
                    em.persist(cart);
                }
                em.flush();
                return newUser.getUserId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(PersistenceException ex) {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")){
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")){
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
    public User retrieveUserByUserId(Long userId) throws UserNotFoundException{
        User user = em.find(User.class, userId);
        if( user != null) {
            return user;
        }
        else {
            throw new UserNotFoundException("User ID " + userId + " does not exist!");
        }
    }
    
   @Override
    public List<User> retrieveAllUsers(){
        Query query = em.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }
    
    
   @Override
    public User retrieveUserByEmail(String email) throws UserNotFoundException{
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :inEmail");
        query.setParameter("inEmail", email);
        
        try {
            return (User)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new UserNotFoundException("User's Email " + email + " does not exist!");
        }
    }
    
   @Override
    public void deleteUser(Long userId) throws UserNotFoundException, DeleteUserException {
        User userToRemove = retrieveUserByUserId(userId);
        if(userToRemove.isIsFlag()){ //if flag then can delete ? yq
            em.remove(userToRemove);
        }
        else{
            throw new DeleteUserException("User ID " + userId + " is associated with existing transaction(s) and cannot be deleted!");
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<User>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
