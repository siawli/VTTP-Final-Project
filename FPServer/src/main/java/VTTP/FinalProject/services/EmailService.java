package VTTP.FinalProject.services;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Value("${email.from}")
    private String fromEmail;

    @Value("${email.password}")
    private String password;

    public void sendEmailUponSignUp(String toEmail) {
        // System.out.println(">>> Sending email out >>>");
        Properties props =  new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMPT HOST
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        // System.out.println(">>> Session created");

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("Content-Transfer-Encoding", "8-bit");
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setSubject("Welcome to Master Kitchen!");
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSentDate(new Date());
            msg.setRecipients
                (Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            
            // create the body part
            BodyPart messageBodyPart = new MimeBodyPart();
            
            String body = getEmailMessage();

            messageBodyPart.setContent(body, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
            // System.out.println(">>> email successfully sent out");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    private String getEmailMessage() {

        String message = """
            <head>
                <style type=\"text/css\">
                    .body {
                        display:-webkit-flex;
                        flex-direction: column;
                        text-align: center;
                    }

                    img {
                        width: 300px;
                    }
                </style>
            </head>
            <div class=\"body\">
                <h1> Welcome to Master Kitchen </h1>
                <img src=\"https://siawli-vttp.sgp1.digitaloceanspaces.com/MasterKitchenLogo.png\"/>
                <p> With a thousand over recipes, you will never run out of food to cook! Search for recipes and share your cooks! </p>
            </div> 
        """;

        return message;
    }

}
