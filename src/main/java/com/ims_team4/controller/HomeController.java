package com.ims_team4.controller;

import com.ims_team4.controller.utils.SessionController;
import com.ims_team4.controller.utils.SetupSidebar;
import com.ims_team4.service.UserService;
import com.ims_team4.service.impl.EmployeeServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class HomeController {
    private final SessionController details;

    public HomeController(EmployeeServiceImpl impl, UserService uSrv) {
        this.details = new SessionController(impl, uSrv);
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    /**
     * Return to dashboard homepage with information of user that has logged in.
     *
     * @return view dashboard.html
     * @author HoanTX
     */
    @GetMapping("/dashboard")
    public String dashboard(@NotNull Model model, HttpSession session, Locale locale) {
        return "component/dashboard";
    }

    @GetMapping("/error")
    public String redirectToAccessDeniedPage() {
        return "component/access-denied";
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));
    }
}
