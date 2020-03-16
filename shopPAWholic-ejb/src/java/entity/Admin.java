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
    
    


}
