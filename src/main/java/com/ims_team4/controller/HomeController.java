package com.ims_team4.controller;

import com.ims_team4.controller.utils.SessionController;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.UserService;
import com.ims_team4.service.impl.EmployeeServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final SessionController details;

    public HomeController(EmployeeServiceImpl impl, UserService uSrv) {
        this.details = new SessionController(impl, uSrv);
    }

    @GetMapping("/")
    public String index() {
        return "login-logout-features/login";
    }

    /**
     * Return to dashboard homepage with information of user that has logged in.
     *
     * @return view dashboard.html
     * @author HoanTX
     */
    @GetMapping("/dashboard")
    public String dashboard(@NotNull Model model, HttpSession session) {
        details.throwDataToTopSidebar(session, model);
        return "component/dashboard";
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));
    }
}
