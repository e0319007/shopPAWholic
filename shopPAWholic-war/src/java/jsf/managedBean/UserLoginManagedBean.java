/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author yeeqinghew
 */
@Named(value = "customerLoginManagedBean")
@RequestScoped
public class UserLoginManagedBean {

    public UserLoginManagedBean() {
    }
    
}
