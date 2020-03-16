/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "loginManagedBean")
@RequestScoped
public class LoginManagedBean {

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;
    private String email;
    private String password;

    /**
     * Creates a new instance of LoginManagedBean
     */
    
    public LoginManagedBean() {
    }
    
    public void clientLogin(ActionEvent event) throws IOException { 
        
    }
    
    public void adminLogin(ActionEvent event) throws IOException {
        
    }
    
}
