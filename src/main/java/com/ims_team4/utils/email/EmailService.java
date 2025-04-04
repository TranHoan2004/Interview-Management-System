package com.ims_team4.utils.email;

import com.ims_team4.config.Constants;

public interface EmailService extends Constants.MailProperties {
    void sendNormalEmail(String to, String subject, String body);

    void sendEmailAttachFile(String to, String subject, String body, String path);

}
