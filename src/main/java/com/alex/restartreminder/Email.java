package com.alex.restartreminder;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Email Class
 *
 * Constructs and sends a singlepart email (RFC822).
 *
 * Email components include sender, recepient, subject, and
 * email body.
 */
public class Email {

    /* Default email credentials and settings */
    private String host = "smtp.gmail.com", username = "gmail_username", password = "gmail_password";
    private int port = 587;

    /* Default email information */
    private String emailSender = "gmail_username@gmail.com", emailRecepient = "",
                    emailSubject = "Email Subject - Restart Reminder",
                    emailBody = "System restart recommended.";

    /**
     * Email Constructor
     *
     * Uses all default values to construct emails.
     */
    public Email() {
        /* Use default credentials and settings */
    }

    /**
     * Overloaded Email Constructor
     *
     * Change the default email credentials and settings.
     *
     * @param host      email hostname SMTP
     * @param port      required port for the host
     * @param username  username for the account sending the email (doesn't require @gmail.com)
     * @param password  password for the account sending the email
     */
    public Email(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructs and sends singlepart email.
     */
    public void sendEmail() {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.ssl.trust", host);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailSender));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecepient));
            msg.setSubject(emailSubject);
            msg.setText(emailBody);
            Transport.send(msg);

            System.out.println("Email sent successfully! " + new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* Getter and setter methods */

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getEmailRecepient() {
        return emailRecepient;
    }

    public void setEmailRecepient(String emailRecepient) {
        this.emailRecepient = emailRecepient;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}
