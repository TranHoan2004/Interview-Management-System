package com.ims_team4.controller.utils;

import com.ims_team4.controller.LoginController;
import com.ims_team4.controller.UserController;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SessionController {
    private final EmployeeService empSrv;
    private final UserService uSrv;

    public SessionController(EmployeeService empSrv, UserService uSrv) {
        this.empSrv = empSrv;
        this.uSrv = uSrv;
    }

    @Nullable
    public UserDetails getUserDetailsFromSession(@NotNull HttpSession session) {
        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails user = (UserDetails) authentication.getPrincipal();
                Logger.getLogger(LoginController.class.getName()).log(Level.FINE, user == null ? "user detail null" : user.toString());
                return user;
            }
        } else {
            Logger.getLogger(LoginController.class.getName()).log(Level.WARNING, "No SecurityContext found in session");
        }
        return null;
    }

    public void throwDataToTopSidebar(HttpSession session, @NotNull Model model) {
        EmployeeDTO employeeDTO = getEntityFromSession(session);
        Logger logger = Logger.getLogger(SessionController.class.getName());
        logger.info(employeeDTO.toString());
        session.setAttribute("account", getEntityFromSession(session));
    }

    private EmployeeDTO getEntityFromSession(HttpSession session) {
        UserDetails detail = getUserDetailsFromSession(session);
        EmployeeDTO account = EmployeeDTO.builder().build();
        try {
            if (detail != null) {
                List<UserDTO> list = uSrv.getUserByEmail(detail.getUsername());
                UserDTO user = list.getFirst();
                account = empSrv.getEmployeeById(Math.toIntExact(user.getId()));
            }
        } catch (Exception e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return account;
    }
}
