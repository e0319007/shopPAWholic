package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
import java.io.IOException;
import javafx.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import util.exception.InvalidLoginCredentialException;

@Named(value = "adminLoginManagedBean")
@RequestScoped
public class AdminLoginManagedBean {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    private String username;
    private String password;

    public AdminLoginManagedBean() throws MessagingException {
//        System.out.println("********** readEmail");
//        getEmailNumber();

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

//    public void getEmailNumber() throws NoSuchProviderException, MessagingException {
//
////        URLName url = new URLName("imaps", "imap.gmail.com", 993, "INBOX", "shoppawholic@gmail.com", "shoppawholic2020");
////        Properties props = null;
////        try {
////            props = System.getProperties();
////        } catch (SecurityException sex) {
////            props = new Properties();
////        }
////        Session session = Session.getDefaultInstance(props, null);
////        Store store = session.getStore(url);
////        store.connect();
////        Folder folder = store.getFolder(url);
////        folder.open(Folder.READ_ONLY);
////
////        Message[] messages = folder.getMessages();
////        System.out.println("messages.length---" + messages.length);
//
//        try {
//
//            //create properties field
//            Properties properties = new Properties();
//
//            properties.put("mail.host", "pop.gmail.com");
//            properties.put("mail.pop3.port", "995");
//            properties.put("mail.pop3.auth", "true");
//            properties.put("mail.store.protocol", "pop3s");
//            
////            properties.put("mail.smtp.ssl.trust", "pop.gmail.com");
////            properties.put("mail.pop3.starttls.enable", "true");
//
//            Session emailSession = Session.getDefaultInstance(properties, null);
//            System.out.println("******** emailSession" + emailSession);
//            //create the POP3 store object and connect with the pop server
//            Store store = emailSession.getStore();
//
//            store.connect("shoppawholic@gmail.com", "shoppawholic2020");
//            System.out.println("********* store" + store);
//            //create the folder object and open it
//            Folder emailFolder = store.getFolder("INBOX");
//            emailFolder.open(Folder.READ_ONLY);
//
//            // retrieve the messages from the folder in an array and print it
//            Message[] messages = emailFolder.getMessages();
//            System.out.println("messages.length---" + messages.length);
//
//            for (int i = 0, n = messages.length; i < n; i++) {
//                Message message = messages[i];
//                System.out.println("---------------------------------");
//                System.out.println("Email Number " + (i + 1));
//                System.out.println("Subject: " + message.getSubject());
//                System.out.println("From: " + message.getFrom()[0]);
//                System.out.println("Text: " + message.getContent().toString());
//
//            }
//
//            //close the store and folder objects
//            emailFolder.close(false);
//            store.close();
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
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
}
