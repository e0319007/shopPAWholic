package ejb.session.stateless;

import entity.Cart;
import entity.Customer;
import entity.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
    public User userLogin(String email, String password) throws InvalidLoginCredentialException {
        try {
            User user = retrieveUserByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + user.getSalt()));

            if (user.getPassword().equals(passwordHash)) {
                return user;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (UserNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    //create new user 
    @Override
    public Long createNewUser(User newUser) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(newUser);
            if (constraintViolations.isEmpty()) {
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
    
    /*@Override
    public void updateUser(User user) throws UserNotFoundException, InputDataValidationException {
        if(user != null && user.getUserId() !=null) {
            Set<ConstraintViolation<User>>constraintViolations = validator.validate(user); 
            if(constraintViolations.isEmpty()) {
                User userToUpdate = retrieveUserByUserId(user.getUserId()); 
                if(userToUpdate.getEmail().equals(user.getEmail())) {
                    userToUpdate.setName(user.getName());
                    userToUpdate.setContactNumber(user.getContactNumber());
                    userToUpdate.setPassword(user.getPassword());
                } else {
                    throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            } else {
                throw new UserNotFoundException("User ID not provided for user to be updated");
            }
        }
    }*/
    @Override
    public void updateUser(User userToUpdate) {
        try {
            User user = retrieveUserByUserId(userToUpdate.getUserId());
            user.setName(userToUpdate.getName());
            user.setContactNumber(userToUpdate.getContactNumber());   
            user.setEmail(userToUpdate.getEmail());
   
        } catch (UserNotFoundException ex) {
            Logger.getLogger(EventSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void updateEmail(User userToUpdate, String password) {
        try {
            User user = retrieveUserByUserId(userToUpdate.getUserId());
            user.setPassword(password);
            em.persist(user);
   
        } catch (UserNotFoundException ex) {
            Logger.getLogger(EventSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User retrieveUserByUserId(Long userId) throws UserNotFoundException {
        User user = em.find(User.class, userId);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User ID " + userId + " does not exist!");
        }
    }

    @Override
    public List<User> retrieveAllUsers() {
        Query query = em.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }
    
    @Override 
    public List<String> retrieveAllEmails(){
        Query query = em.createQuery("SELECT u.email FROM User u");
        return query.getResultList();
    }

    @Override
    public User retrieveUserByEmail(String email) throws UserNotFoundException {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :inEmail");
        query.setParameter("inEmail", email);

        try {
            return (User) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new UserNotFoundException("User's Email " + email + " does not exist!");
        }
    }

    @Override
    public Map<String, Integer> retrieveTotalNumberOfUsersForTheYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Query query;
        Map<String, Integer> userPerMonth = new HashMap<>();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        for (int i = 0; i < months.size(); i++) {
            query = em.createQuery("SELECT u FROM User u WHERE EXTRACT(YEAR(u.accCreatedDate)) = :inYear and EXTRACT(MONTH(u.accCreatedDate)) = :inMonth");
            query.setParameter("inYear", year);
            query.setParameter("inMonth", i + 1);
            userPerMonth.put(months.get(i), (query.getResultList()).size());
        }
        return userPerMonth;
    }

    @Override
    public Map<String, Integer> retrieveTotalNumberOfUsersForDay() {
        Query query;
        long days_in_ms = 1000 * 60 * 60 * 24;
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() - (7 * days_in_ms));
        List<Date> sevenDays = new ArrayList<>();
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
        sevenDays.add(startDate);

        while (endDate.before(startDate)) {
            try {
                sevenDays.add(dformat.parse(dformat.format(endDate)));
                endDate = new Date(endDate.getTime() + (days_in_ms));
            } catch (ParseException ex) {
                Logger.getLogger(UserSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Map<String, Integer> sevenDaysCast = new LinkedHashMap<>();

        for (int i = 0; i < sevenDays.size(); i++) {
            query = em.createQuery("SELECT u FROM User u WHERE u.accCreatedDate between :inStartDate AND :inEndDate");
            query.setParameter("inStartDate", sevenDays.get(i));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sevenDays.get(i));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 0);

            query.setParameter("inEndDate", calendar.getTime());

            List<User> totalNumber = query.getResultList();
            sevenDaysCast.put(dformat.format(sevenDays.get(i)), totalNumber.size());
        }
        return sevenDaysCast;
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException, DeleteUserException {
        User userToRemove = retrieveUserByUserId(userId);
        if (userToRemove.isIsFlag()) { //if flag then can delete ? yq
            em.remove(userToRemove);
        } else {
            throw new DeleteUserException("User ID " + userId + " is associated with existing transaction(s) and cannot be deleted!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<User>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
