package com.ims_team4.controller.utils;

import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SessionController {
    private final EmployeeService empSrv;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public SessionController(EmployeeService empSrv) {
        this.empSrv = empSrv;
    }

    @Nullable
    public String getEmailFromSession(@NotNull HttpSession session) {
        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (context != null) {
            logger.info("context not null " + context.getAuthentication().getPrincipal());
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                if (authentication.getPrincipal() instanceof UserDetails user) {
                    logger.info("user details: " + user.getUsername());
                    logger.log(Level.FINE, "getUserDetailsFromSession: " + user);
                    return user.getUsername();
                }
            }
        } else {
            logger.log(Level.WARNING, "No SecurityContext found in session");
        }
        return null;
    }

    public EmployeeDTO getEntityFromSession(HttpSession session) {
        logger.info("getEntityFromSession");
        String email = getEmailFromSession(session);
        logger.info("Get email from session: " + email);
        EmployeeDTO account = EmployeeDTO.builder().build();
        if (email != null) {
            account = empSrv.getEmployeeDTOByEmail(email);
            if (account == null) {
                throw new UsernameNotFoundException("Account not found");
            }
        }
        return account;
    }

    public void setAtributeForSecurityContext(@NotNull EmployeeDTO account, @NotNull HttpSession session) {
        SecurityContext context = SecurityContextHolder.getContext();;
        GrantedAuthority ga = new SimpleGrantedAuthority(account.getRole().name());
        UserDetails details = new User(account.getEmail(), account.getPassword(), List.of(ga));
        Authentication auth = new UsernamePasswordAuthenticationToken(details, account.getPassword(), List.of(ga));
        context.setAuthentication(auth);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        System.out.println(session.getAttribute("SPRING_SECURITY_CONTEXT"));
    }
}
