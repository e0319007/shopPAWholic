/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import entity.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import util.exception.InvalidLoginCredentialException;

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

    public void login(javafx.event.ActionEvent event) throws IOException {
        try {
            User currentUser = userSessionBeanLocal.userLogin(getEmail(), getPassword());
            if (currentUser instanceof Customer) {
                FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", currentUser);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You are now loggeed in as Customer", null));
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperation/customerHomepage.xhtml");
            } else if (currentUser instanceof Seller) {
                FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", currentUser);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You are now loggeed in as Seller", null));
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/sellerOperation/sellerHomepage.xhtml");
            }
        } catch (InvalidLoginCredentialException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }

    public void logout(javafx.event.ActionEvent event) throws IOException {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
