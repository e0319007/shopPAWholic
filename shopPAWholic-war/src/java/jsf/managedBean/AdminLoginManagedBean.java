package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
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
//        final Properties props = new Properties();
//       props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.setProperty("mail.pop3.socketFactory.fallback", "false");
//        props.setProperty( "mail.pop3.host", "pop.gmail.com" );
//        props.setProperty( "mail.pop3.user", "shoppawholic");
//        props.setProperty( "mail.pop3.password", "shoppawholic2020");
//        props.setProperty( "mail.pop3.ssl.enable", "true");
//        props.setProperty( "mail.pop3.port", "995" );
//        props.setProperty( "mail.pop3.auth", "true" );      
//        props.setProperty("mail.pop3.starttls.enable", "true"); 
//       /* props.setProperty( "mail.pop3.starttls.enable", "true" );
//        props.setProperty( "mail.pop3.starttls.required", "true" );*/
//
//        Session session  = Session.getInstance(props);
//        session.setDebug(true);
//
//        Store store = session.getStore("pop3");
//        store.connect("shoppawholic", "shoppawholic2020");         
//        Folder folder = store.getDefaultFolder();
//        folder.open(Folder.READ_ONLY);
//        Message message[] = folder.getMessages();
//        for ( int i = 0; i < message.length; i++ )
//        {
//          Message m = message[i];
//              System.out.println( "-------------------------\nNachricht: " + i );
//              System.out.println( "From: " + Arrays.toString(m.getFrom()) );
//              System.out.println( "Topic: " + m.getSubject() );   
//
//
//          if ( m.isMimeType("text/plain") )
//            try {
//                System.out.println( m.getContent() );
//          } catch (IOException ex) {
//              Logger.getLogger(AdminLoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
//          }
//        }
//        folder.close( false );
//        store.close();
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
