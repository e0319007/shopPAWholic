package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Local
public interface CustomerSessionBeanLocal {

    public Long createNewCustomer(Customer newCustomer) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public List<Customer> retrieveAllCustomers();

}
