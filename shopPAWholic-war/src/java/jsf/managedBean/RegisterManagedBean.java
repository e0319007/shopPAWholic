package jsf.managedBean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Named(value = "registerManagedBean")
@ViewScoped
public class RegisterManagedBean implements Serializable{

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private String role;
    private String name;
    private String email;
    private String password;
    private String contactNumber;
    
    
    public RegisterManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct(){
        
    }
    
    public void createNewUser(ActionEvent event) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException, IOException{
        if (role.equals("Customer")){
            System.out.println("I am in Customer");
            customerSessionBeanLocal.createNewCustomer(new Customer(name, email, contactNumber, password));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You can now login as customer.", null));
        } else if (role.equals("Seller")){
            System.out.println("I am in Seller");
            sellerSessionBeanLocal.createNewSeller(new Seller(name, email, contactNumber, password, false, 5));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You can now login as seller.", null));

        }

    }
    
    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
