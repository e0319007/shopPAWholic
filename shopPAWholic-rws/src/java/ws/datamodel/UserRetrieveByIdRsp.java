/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.User;

/**
 *
 * @author Joanna Ng
 */
public class UserRetrieveByIdRsp {
    
    private User user;

    public UserRetrieveByIdRsp() {
    }

    public UserRetrieveByIdRsp(User user) {
        this.user = user;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
