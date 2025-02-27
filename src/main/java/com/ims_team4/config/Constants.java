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

    interface Regex {
        String EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{2,})(\\\\.)(.{2,})";
        String PHONE_REGEX = "^[0-9]{10,11}$";
    }

    interface GoogleAndFacebookAuthentication {
        // Google
        String GOOGLE_LINK_GET_TOKEN = "";
        String GOOGLE_CLIENT_ID = "";
        String GOOGLE_CLIENT_SECRET = "";
        String GOOGLE_REDIRECT_URI = "";
        String GOOGLE_GRANT_TYPE = "";
        String GOOGLE_LINK_GET_USER_INFO = "";

        // Facebook
        String FACEBOOK_CLIENT_ID = "";
        String FACEBOOK_CLIENT_SECRET = "";
        String FACEBOOK_REDIRECT_URI = "";
        String FACEBOOK_LINK_GET_TOKEN = "";
        String FACEBOOK_LINK_GET_USER_INFO = "";
    }

}
