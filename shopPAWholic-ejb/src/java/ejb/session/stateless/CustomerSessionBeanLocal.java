package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewCartException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Local
public interface CustomerSessionBeanLocal {

    public Long createNewCustomer(Customer newCustomer) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public List<Customer> retrieveAllCustomers();

    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException;

    public void updateCustomer(Customer customer) throws CustomerNotFoundException, InputDataValidationException;

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;

}
