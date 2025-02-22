package com.ims_team4.utils.email;


import com.ims_team4.config.Constants;

public interface EmailService extends Constants.MailProperties {
    void sendEmail(String to, String subject, String body);
}
