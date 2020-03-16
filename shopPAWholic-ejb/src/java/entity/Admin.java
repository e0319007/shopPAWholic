/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Joanna Ng
 */
@Entity
public class Admin extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;

    
    public Admin() {
    }
    
    public Admin(String username) {
        this();
        this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
