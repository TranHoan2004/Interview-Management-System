package com.ims_team4.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ims_team4.config.Constants;
import com.ims_team4.controller.utils.SessionController;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.dto.authentication_entities.GoogleAccount;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.UserService;
import com.ims_team4.service.impl.EmployeeServiceImpl;
import com.ims_team4.service.impl.UserServiceImpl;
import com.ims_team4.utils.RandomCode;
import com.ims_team4.utils.email.EmailService;
import com.ims_team4.utils.email.EmailServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
// HoanTX
public class LoginController implements Constants.GoogleAndFacebookAuthentication {
    private static String randomCode;
    private static EmployeeDTO employeeDTO = new EmployeeDTO();
    private final UserService userService;
    private final EmployeeService empSrv;
    private final SessionController details;
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    public LoginController(UserServiceImpl impl, EmployeeService empSrv, EmployeeServiceImpl empSrvImpl) {
        this.userService = impl;
        this.empSrv = empSrvImpl;
        this.details = new SessionController(empSrv);
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
     */
    @GetMapping("/login")
    public String login(@RequestParam(name = "code", required = false) String code,
                        @RequestParam(value = "error", required = false) boolean error,
                        Model model, HttpSession session) {
        logger.info("login get method");
        render(model);
        try {
            if (error) {
                throw new Exception("Error occurred");
            }
            if (code == null) {
                return "login-logout-features/login";
            }
            String accessToken = getGoogleToken(code);
            String email = getGoogleUserInfo(accessToken).getEmail();
            setupSession(session, email);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            return "login-logout-features/login";
        }
        return "redirect:/dashboard";
    }

    // <editor-fold> desc="forgot password"
    @GetMapping("/forgotPassword")
    public String redirectToResetPasswordScreen(Model model) {
        model.addAttribute("email", String.class);
        model.addAttribute("status", true);
        model.addAttribute("error", null);
        return "login-logout-features/reset-password";
    }

    @PostMapping("/verify-email")
    public String checkEmailAndSendVerifyCode(Model model, @RequestParam("email") String email) {
        logger.info("email: " + email);
        try {
            List<String> list = userService.getAllEmail();
            logger.info(list.toString());
            if (!isEmailExisting(email, list)) {
                throw new Exception("Email not found");
            }
            UserDTO userDTO = userService.getUserByEmail(email);
            employeeDTO = empSrv.getEmployeeById(Math.toIntExact(userDTO.getId()));
            sendEmail(email);
            model.addAttribute("status", false);
            model.addAttribute("code", String.class);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("status", true);
        }
        return "login-logout-features/reset-password";
    }

    @PostMapping("/verify-code")
    public String verifyCode(@ModelAttribute("code") String code, Model model) {
        try {
            logger.info("code in model attribute: " + code + " and random code: " + randomCode);
            if (!code.equals(randomCode)) {
                throw new Exception("Wrong code");
            }
            model.addAttribute("status", null);
            model.addAttribute("password", String.class);
            model.addAttribute("rewrite-password", String.class);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("status", false);
        }
        return "login-logout-features/reset-password";
    }

    @PostMapping("/change-password")
    public String setPassword(@RequestParam("password") String password, Model model) {
        try {
            employeeDTO.setPassword(password);
            empSrv.updateEmployeesPassword(employeeDTO);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            return "login-logout-features/reset-password";
        }
        return "redirect:/login";
    }
    // </editor-fold>

    // <editor-fold> desc="private method"
    // <editor-fold> desc="Google Login utils"
    @NotNull
    private static String getGoogleToken(String code) throws Exception {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form
                        .form().add("client_id", GOOGLE_CLIENT_ID)
                        .add("client_secret", GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", GOOGLE_REDIRECT_URI)
                        .add("code", code).add("grant_type", GOOGLE_GRANT_TYPE)
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

    private boolean isEmailExisting(@NotNull String email, @NotNull List<String> list) {
        logger.info("verify email: " + email);
        return list.stream().anyMatch(email::equals);
    }

    private void sendEmail(String email) {
        EmailService service = new EmailServiceImpl();
        randomCode = RandomCode.generateSixRandomCodes();
        logger.info("random code: " + randomCode);
        String subject = """
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
                                                        style="background-color: #ffffff; border-radius: 8px; overflow: hidden;\s
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
                                                             <div style="font-size: 28px; font-weight: bold; color: #ff6600;\s
                                                                         margin: 20px 0;">
                                                                \s""" + randomCode
                         + """
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
        service.sendNormalEmail(email, "[Interview Management System] Verify Code", subject);
    }

    private void render(@NotNull Model model) {
        model.addAttribute("client_id", GOOGLE_CLIENT_ID);
        model.addAttribute("client_secret", GOOGLE_CLIENT_SECRET);
        model.addAttribute("redirect_uri", GOOGLE_REDIRECT_URI);
    }

    private void setupSession(HttpSession session, String email) throws Exception {
        EmployeeDTO emp = empSrv.getEmployeeDTOByEmail(email);
        if (emp == null) {
            throw new Exception("User not found");
        }
        session.setAttribute("account", emp);
        details.setAttributeForSecurityContext(emp, session);
        logger.info("session: " + session.getAttribute("account"));
    }
    // </editor-fold>
}
