package com.ims_team4.config;

public interface Constants {
    interface MailProperties {
        String HOST_NAME = "smtp.gmail.com";
        int PORT = 465;
        String APP_EMAIL = "myemail";
        String APP_PASSWORD = "mypassword";
        // get app password from 2 steps verification of Google
    }

    interface QRProperties {
        String BANK_CODE = "";
        String ACCOUNT_NUMBER = "";
        String ACCOUNT_NAME = "";
    }
}
