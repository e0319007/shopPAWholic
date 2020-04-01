/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.User;
import java.util.List;

/**
 *
 * @author Joanna Ng
 */
public class UserRetrieveAllRsp {
    
    private List<User> users;

    public UserRetrieveAllRsp() {
    }

    public UserRetrieveAllRsp(List<User> users) {
        this.users = users;
    }

    /**
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
    
}
