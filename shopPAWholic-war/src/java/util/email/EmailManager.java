package util.email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {

    private final String emailServerName = "smtp.gmail.com";
    private final String mailer = "JavaMailer";
    private String smtpAuthUser;
    private String smtpAuthPassword;
    
    public EmailManager() {
    }

    public EmailManager(String smtpAuthUser, String smtpAuthPassword) {
        this.smtpAuthUser = smtpAuthUser;
        this.smtpAuthPassword = smtpAuthPassword;
    }

    public Boolean email(String fromEmailAddress, String toEmailAddress) {
        String emailBody = "Hello, you've registered an account with shopPAWholic.";

        try {
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailServerName);
            props.put("mail.smtp.ssl.trust", emailServerName);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");
            javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            Message msg = new MimeMessage(session);

            if (msg != null) {
                msg.setFrom(InternetAddress.parse(fromEmailAddress, false)[0]);
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress, false));
                msg.setSubject("shopPAWholic");
                msg.setText(emailBody);
                msg.setHeader("X-Mailer", mailer);

                Date timeStamp = new Date();
                msg.setSentDate(timeStamp);

                Transport.send(msg);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }
    
    public Message[] readEmail() throws NoSuchProviderException, MessagingException {
        final Properties props = new Properties();
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.host", "pop.gmail.com");
        props.setProperty("mail.pop3.user", "shoppawholic");
        props.setProperty("mail.pop3.password", "shoppawholic2020");
        props.setProperty("mail.pop3.ssl.enable", "true");
        props.setProperty("mail.pop3.port", "995");
        props.setProperty("mail.pop3.auth", "true");
        props.setProperty("mail.pop3.starttls.enable", "true");
        props.setProperty("mail.protocol.ssl.trust", "pop.gmail.com");

        Session session = Session.getInstance(props);
        session.setDebug(true);

        Store store = session.getStore("pop3");
        store.connect("shoppawholic", "shoppawholic2020");
        Folder folder = store.getFolder("INBOX");;
        folder.open(Folder.READ_ONLY);
        Message [] messageList = folder.getMessages();
        
//        for (int i = 0; i < message.length; i++) {
//            Message m = message[i];
//            System.out.println("-------------------------\nNachricht: " + i);
//            System.out.println("From: " + Arrays.toString(m.getFrom()));
//            System.out.println("Topic: " + m.getSubject());
//        }
        folder.close(false);
        store.close();
        return messageList;
    }
}
       
