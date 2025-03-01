package com.ims_team4.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ims_team4.config.Constants;
import com.ims_team4.controller.utils.GetUserDetailsFromSession;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.dto.authentication_entities.FacebookAccount;
import com.ims_team4.dto.authentication_entities.GoogleAccount;
import com.ims_team4.service.UserService;
import com.ims_team4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class LoginController implements Constants.GoogleAndFacebookAuthentication {
    private final UserService userService;
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    private final GetUserDetailsFromSession details;
    private final String LOGIN_TYPE_FACEBOOK = "facebook";
    private final String LOGIN_TYPE_GOOGLE = "google";

    public LoginController(UserServiceImpl impl) {
        this.userService = impl;
        this.details = new GetUserDetailsFromSession();
    }

    /**
     * Login by Facebook or Google module. <br/> Args: {@code code}, {@code error}, {@code type}
     * <p>
     * When we send authorization request to Facebook and Google (in the login page), we will receive authorization
     * grant uri contains authorized {@code code} and value of state.
     * </p>
     * <p>
     * We use this {@code code} value to send an authorization grant uri to get an access token.
     * </p>
     * <p>
     * After having an access token, we send it to resource server to get account information. Finally, they will send
     * us back all information of log in account.
     * </p>
     * <p>
     * If login process has {@code error}, we will throw that {@code error} to login page that do not solve anything
     * with OAuth2 authentication system.
     * </p>
     *
     * @author HoanTX
     */
    @GetMapping("/login")
    public String login(@RequestParam(name = "code", required = false) String code,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(name = "state", required = false) String type,
                        Model model,
                        HttpSession session) {
        String email = "";
        String accessToken;
        try {
            if (error) {
                throw new Exception("Error occurred");
            }
            if (code != null && type != null) {
                switch (type) {
                    case LOGIN_TYPE_FACEBOOK -> {
                        accessToken = getFacebookToken(code);
                        email = getFacebookUserInfo(accessToken).getEmail();
                    }
                    case LOGIN_TYPE_GOOGLE -> {
                        accessToken = getGoogleToken(code);
                        email = getGoogleUserInfo(accessToken).getEmail();
                    }
                }
                Optional<UserDTO> user = userService.getUserByEmail(email);
                if (user.isPresent()) {
                    session.setAttribute("account", user.get());
                } else {
                    throw new UsernameNotFoundException("User not found");
                }
                details.getUserDTOFromSession(session);
            } else {
                return "login-logout-features/login";
            }
        } catch (UsernameNotFoundException u) {
            logger.log(Level.SEVERE, u.getMessage(), u);
            model.addAttribute("errorMessage", u.getMessage());
            return "login-logout-features/login";
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return "login-logout-features/login";
        }
        return "index";
    }

    // HoanTX
    // <editor-fold> desc="Google Login utils"
    @NotNull
    private static String getGoogleToken(String code) throws Exception {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN).bodyForm(
                        Form
                                .form()
                                .add("client_id", GOOGLE_CLIENT_ID)
                                .add("client_secret", GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", GOOGLE_GRANT_TYPE)
                                .build())
                .execute().returnContent().asString();
        JsonObject obj = new Gson().fromJson(response, JsonObject.class);
        if (!obj.has("access_token")) {
            throw new IOException("Failed to retrieve access token from Google");
        }
        return obj.get("access_token").toString().replaceAll("\"", "");
    }

    private static GoogleAccount getGoogleUserInfo(final String accessToken) throws Exception {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        return new Gson().fromJson(response, GoogleAccount.class);
    }
    // </editor-fold>

    // HoanTX
    // <editor-fold> desc="Facebook Login utils"
    @NotNull
    private static String getFacebookToken(String code) throws Exception {
        String response = Request.Post(FACEBOOK_LINK_GET_TOKEN).bodyForm(
                        Form
                                .form()
                                .add("client_id", FACEBOOK_CLIENT_ID)
                                .add("client_secret", FACEBOOK_CLIENT_SECRET)
                                .add("redirect_uri", FACEBOOK_REDIRECT_URI)
                                .add("code", code)
                                .build())
                .execute().returnContent().asString();
        JsonObject obj = new Gson().fromJson(response, JsonObject.class);
        if (!obj.has("access_token")) {
            throw new IOException("Failed to retrieve access token from Facebook");
        }
        return obj.get("access_token").toString().replaceAll("\"", "");
    }

    private static FacebookAccount getFacebookUserInfo(final String accessToken) throws Exception {
        String link = FACEBOOK_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        return new Gson().fromJson(response, FacebookAccount.class);
    }
    // </editor-fold>
}
