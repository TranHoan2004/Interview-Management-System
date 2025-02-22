package com.ims_team4.utils.email;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {
    @Bean
    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(getSession());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            // send message
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", HOST_NAME);
        properties.put("mail.smtp.socketFactory.port", PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", PORT);
        return properties;
    }

    private Session getSession() {
        return Session.getDefaultInstance(getProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });
    }

    // test
//    public static void main(String[] args) {
//        EmailService mailService = new EmailServiceImpl();
//        mailService.sendEmail("youremail", "Subject", "Text");
//    }
}
