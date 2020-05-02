package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import util.email.EmailManager;
import util.exception.InvalidLoginCredentialException;

@Named(value = "adminLoginManagedBean")
@RequestScoped
public class AdminLoginManagedBean implements Serializable {

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    //for login
    private String username;
    private String password;

    //for composing email
    private String to;
    private String subject;
    private String emailBody;
    List<String> recipients;

    public AdminLoginManagedBean() {
        recipients = new ArrayList<>();
    }

    public void login(ActionEvent event) throws IOException {
        try {
            Admin currentAdmin = adminSessionBeanLocal.adminLogin(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentAdmin", currentAdmin);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/adminOperation/adminHomepage.xhtml");
        } catch (InvalidLoginCredentialException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }

    public void logout(ActionEvent event) throws IOException {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You are now logged out.", null));
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    public Message[] readEmail(ActionEvent event) throws MessagingException {
        EmailManager emailManager = new EmailManager("shoppawholic@gmail.com", "shoppawholic2020");
        Message[] messages = emailManager.readEmail();
        return messages;
    }

    public void sendEmail(ActionEvent event) {
        System.out.println("##############################" + to);
        System.out.println("##############################" + recipients);
        System.out.println("##############################" + subject);
        System.out.println("##############################" + emailBody);
        EmailManager emailManager = new EmailManager("shoppawholic@gmail.com", "shoppawholic2020");
        if (to.equals("All")) {
            recipients = userSessionBeanLocal.retrieveAllEmails();
        } else if (to.equals("Customers")) {
            recipients = userSessionBeanLocal.retrieveAllCustomersEmails();
        } else if (to.equals("Sellers")) {
            recipients = userSessionBeanLocal.retrieveAllSellersEmails();
        }

        System.out.println("##############################1" + recipients);
        System.out.println("##############################1" + subject);
        System.out.println("##############################1" + emailBody);
        Boolean emailResult = emailManager.broadcastEmail(recipients, "shoppawholic@gmail.com", getSubject(), getEmailBody());
        if (emailResult.equals(true)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your email has sent out successfuly.", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email failed to send.", null));
        }
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

    public void authorized(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You need to login.", null));
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    public String getEmailBody() {
        return emailBody;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
