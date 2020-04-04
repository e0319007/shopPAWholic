package util.email;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

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

    
//        Session session = Session.getDefaultInstance(new Properties());
//        Store store = session.getStore("imaps");
//        store.connect("imap.googlemail.com", 993, "shoppawholic@gmail.com", "shoppawholic2020");
//        Folder inbox = store.getFolder("INBOX");
//        inbox.open(Folder.READ_ONLY);
//
//        // Fetch unseen messages from inbox folder
//        Message[] messages = inbox.search(
//                new FlagTerm(new Flags(Flags.Flag.SEEN), false));
//
//        // Sort messages from recent to oldest
//        Arrays.sort(messages, (m1, m2) -> {
//            try {
//                return m2.getSentDate().compareTo(m1.getSentDate());
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        for (Message message : messages) {
//            System.out.println(
//                    "sendDate: " + message.getSentDate()
//                    + " subject:" + message.getSubject());
//        }

//        Properties props = System.getProperties();
//        props.setProperty("mail.store.protocol", "imaps");
//        Session session = Session.getDefaultInstance(props, null);
//
//        try {
//            Store store = session.getStore();
//            store.connect("imap.gmail.com", 993, "shoppawholic@gmail.com", "shoppawholic2020");
//            System.out.println("************* Store" + store);
//            
//            Folder folder = store.getFolder("INBOX");
//            folder.open(Folder.READ_ONLY);
//            
//            Flags seen = new Flags(Flags.Flag.SEEN);
//            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
////            Message[] msgs = folder.getMessages();?
//            Message messages[] = folder.search(unseenFlagTerm);
//            
//            if(messages.length == 0){
//                System.out.println("No messages found.");
//            } else {
//                System.out.println("******************" + messages.length);
//            }
//
////            for (Message msg : msgs) {
////                System.out.println("***************** Message" + msg);
////                System.out.println(msg.getSubject());
////            }
//        } catch (MessagingException e) {
//            System.out.println(e);
//        }
//    }
}
//        
