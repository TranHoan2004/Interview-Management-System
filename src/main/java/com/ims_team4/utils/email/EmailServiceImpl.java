package com.ims_team4.utils.email;

import com.ims_team4.utils.RandomCode;
import org.jetbrains.annotations.NotNull;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(getSession());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
//            message.setText(body);
            message.setContent(body, "text/html;charset=utf-8");
            // send message
            Transport.send(message);
        } catch (MessagingException e) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.ALL, e.getMessage(), e);
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
        return Session.getInstance(getProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });
    }

    public static void main(String[] args) {
        EmailService mailService = new EmailServiceImpl();
        String randomCode = RandomCode.generateSixRandomCodes();
        String subject =
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Interview Manager System - Verification Code</title>
                        </head>
                        <body style="margin: 0; padding: 0; background-color: #f4f4f4; font-family: Arial, sans-serif;">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                    <td align="center" style="padding: 20px 0;">
                                        <table border="0" cellpadding="0" cellspacing="0" width="600"
                                               style="background-color: #ffffff; border-radius: 8px; overflow: hidden; 
                                                      box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
                                            <tr style="background-color: #ff6600;">
                                                <td align="center" style="padding: 20px 10px;">
                                                    <h2 style="margin: 0; color: #ffffff;">Interview Manager System</h2>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="padding: 30px 40px; text-align: center; color: #333;">
                                                    <h3 style="margin-top: 0; margin-bottom: 10px;">Hello,</h3>
                                                    <p style="margin: 0; font-size: 16px;">
                                                        This is the verify code of <b>Interview Manager System</b>.
                                                    </p>
                                                    <div style="font-size: 28px; font-weight: bold; color: #ff6600; 
                                                                margin: 20px 0;">
                                                        """ + randomCode + """
                                                    </div>
                                                    <p style="margin-top: 20px; font-size: 14px; color: #555;">
                                                        If you do not ask to reset your password, please ignore this email.
                                                    </p>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="background-color: #f4f4f4; padding: 10px; text-align: center;">
                                                    <p style="margin: 0; font-size: 12px; color: #999;">
                                                        © 2023 Interview Manager System. All rights reserved.
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </body>
                        </html>
                        """;
        mailService.sendEmail("", "Subject", subject);
    }
}
