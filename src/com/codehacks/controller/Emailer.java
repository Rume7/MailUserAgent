package com.codehacks.controller;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Rhume
 */
public class Emailer {

    private String sendersEmail;
    private String destinationEmail;
    private String subject;
    private String emailMessage;

    public Emailer(String sender, String receiver, String sub, String msg) {
        this.sendersEmail = sender;
        this.destinationEmail = receiver;
        this.subject = sub;
        this.emailMessage = msg;
    }

    public String getSendersEmail() {
        return sendersEmail;
    }

    public String getDestinationEmail() {
        return destinationEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void send() {
        //Mention user name and password as per your configuration
        final String userName = "username";
        final String pwd = "password";

        //Set properties and their values
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        //Create a Session object & authenticate uid and pwd
        Session sessionobj = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, pwd);
                    }
                });

        try {
            //Create MimeMessage object & set values
            Message messageobj = new MimeMessage(sessionobj);
            messageobj.setFrom(new InternetAddress(getSendersEmail()));
            messageobj.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(getDestinationEmail()));
            messageobj.setSubject(getSubject());
            messageobj.setText(getEmailMessage());

            //Now send the message
            Transport.send(messageobj);
            System.out.println("Your email was sent successfully....");
        } catch (MessagingException exp) {
            throw new RuntimeException(exp);
        }
    }
}
