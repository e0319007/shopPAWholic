package jsf.managedBean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.email.EmailManager;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Named(value = "registerManagedBean")
@ViewScoped
public class RegisterManagedBean implements Serializable {

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private String role;
    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private Date currentDateTime;

    public RegisterManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {

    }

    public void createNewUser(ActionEvent event) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException, IOException {
        if (role.equals("Customer")) {
            currentDateTime = new Date();
            System.out.println("*********************************" + currentDateTime);
            customerSessionBeanLocal.createNewCustomer(new Customer(name, email, contactNumber, password, currentDateTime));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            if (sendEmail(event) == true) {
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You can now login as customer", null));
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured.", null));
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        } else if (role.equals("Seller")) {
            currentDateTime = new Date();
            System.out.println("*********************************" + currentDateTime);
            sellerSessionBeanLocal.createNewSeller(new Seller(name, email, contactNumber, password, currentDateTime, false, 5));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            if (sendEmail(event)) {
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You can now login as seller.", null));

            } else {
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured.", null));
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        }

    }

    public void checkDuplicateEmail() {
        List<String> emailList = userSessionBeanLocal.retrieveAllEmails();
        for (String e : emailList) {
            if (email.equals(e)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplication Email", null));
            }
        }
    }

    public Boolean sendEmail(ActionEvent event) {
        EmailManager emailManager = new EmailManager("shoppawholic@gmail.com", "shoppawholic2020");
        System.out.println(email);
        Boolean result = emailManager.email("shoppawholic@gmail.com", email);
        return result;
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
