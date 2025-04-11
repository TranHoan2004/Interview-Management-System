package com.ims_team4.config;

public interface Constants {
    interface MailProperties {
        String HOST_NAME = "smtp.gmail.com";
        int PORT = 465;
        String APP_EMAIL = "";
        String APP_PASSWORD = "";
        // get app password from 2 steps verification of Google
    }

    interface Regex {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String PHONE_REGEX = "^[0-9]{10,11}$";
    }

    interface GoogleAndFacebookAuthentication {
        String GOOGLE_LINK_GET_TOKEN = "";
        String GOOGLE_CLIENT_ID = "";
        String GOOGLE_CLIENT_SECRET = "";
        String GOOGLE_REDIRECT_URI = "";
        String GOOGLE_GRANT_TYPE = "";
        String GOOGLE_LINK_GET_USER_INFO = "";
    }

    interface Role {
        String ROLE_INTERVIEWER = "INTERVIEWER";
        String ROLE_MANAGER = "MANAGER";
        String ROLE_ADMINISTRATOR = "ADMINISTRATOR";
        String ROLE_RECRUITER = "RECRUITER";
    }

}
