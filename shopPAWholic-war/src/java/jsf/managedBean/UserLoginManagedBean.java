package jsf.managedBean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import util.email.EmailManager;
import util.exception.InvalidLoginCredentialException;

@Named(value = "customerLoginManagedBean")
@RequestScoped
public class UserLoginManagedBean {

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    private String username;
    private String password;
    
    public UserLoginManagedBean() {
        
    }
    public void login(ActionEvent event) throws IOException{
        try{
            User currentUser = userSessionBeanLocal.userLogin(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentAdmin", currentUser);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/afterLogin.xhtml");
        } catch (InvalidLoginCredentialException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }
    
    public void logout(ActionEvent event) throws IOException {
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
