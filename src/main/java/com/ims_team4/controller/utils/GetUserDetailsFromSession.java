package com.ims_team4.controller.utils;

import com.ims_team4.controller.LoginController;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class GetUserDetailsFromSession {
   @Nullable
   public UserDetails getUserDTOFromSession(@NotNull HttpSession session) {
       SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
       if (context != null) {
           Authentication authentication = context.getAuthentication();
           if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
               UserDetails user = (UserDetails) authentication.getPrincipal();
               Logger.getLogger(LoginController.class.getName()).log(Level.FINE, user == null ? "null" : user.toString());
               return user;
           }
       }
       return null;
   }
}
