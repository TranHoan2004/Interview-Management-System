package com.ims_team4.utils.email;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.time.LocalTime;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendNormalEmail(String to, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(getSession());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, "text/html;charset=utf-8");
            // send message
            Transport.send(message);
            log.info("Email sent successfully to {}", to);
        } catch (MessagingException e) {
            log.error("Error sending email: {}", e.getMessage(), e);
        }
    }

    @Override
    public void sendEmailAttachFile(String to, String subject, String body, String path) {
        try {
            MimeMessage message = new MimeMessage(getSession());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(body, "text/html;charset=utf-8");

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(path);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbp);
            multipart.addBodyPart(attachPart);

            message.setContent(multipart);

            // send message
            Transport.send(message);
            log.info("Send email successfully");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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

    @NotNull
    @Contract(" -> new")
    private Session getSession() {
        return Session.getInstance(getProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });
    }

}
