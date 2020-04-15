/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.User;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import util.exception.DeleteUserException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;

/**
 *
 * @author yeeqinghew
 */
@Local
public interface UserSessionBeanLocal {

    public User userLogin(String username, String password) throws InvalidLoginCredentialException;

    public Long createNewUser(User newUser) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public User retrieveUserByUserId(Long userId) throws UserNotFoundException;

    public List<User> retrieveAllUsers();

    public Map<String, Integer> retrieveTotalNumberOfUsersForTheYear();

    public Map<Date, Integer> retrieveTotalNumberOfUsersForDay();
            
    public void deleteUser(Long userId) throws UserNotFoundException, DeleteUserException;

    public User retrieveUserByEmail(String email) throws UserNotFoundException;

}
