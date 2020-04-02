
package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Stateless
@Local(CustomerSessionBeanLocal.class)
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CustomerSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override
    public Long createNewCustomer(Customer newCustomer) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(newCustomer);
            if (constraintViolations.isEmpty()) {
                em.persist(newCustomer);
                em.flush();
                return newCustomer.getUserId();
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
    public List<Customer> retrieveAllCustomer(){
        Query query = em.createQuery("SELECT c FROM Customer c");
        return query.getResultList();
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Customer>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
