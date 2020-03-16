package ejb.session.stateless;

import entity.Admin;
import java.util.List;
import javax.ejb.Local;
import util.exception.AdminNotFoundException;
import util.exception.AdminUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

@Local
public interface AdminSessionBeanLocal {

    public Admin adminLogin(String username, String password) throws InvalidLoginCredentialException;

    public Admin retrieveAdminByAdminId(Long adminId) throws AdminNotFoundException;

    public List<Admin> retrieveAllAdmins();

    public Admin retrieveAdminByUsername(String username) throws AdminNotFoundException;

    public Long createNewAdmin(Admin newAdmin) throws AdminUsernameExistException, UnknownPersistenceException, InputDataValidationException;
    
}
