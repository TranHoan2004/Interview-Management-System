package com.ims_team4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
        // return "index";
    }

    /**
     * Return to dashboard homepage with information of user that has logged in.
     *
     * @return view dashboard.html
     * @author HoanTX
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "component/dashboard";
    }

    @GetMapping("/access_denied")
    public String redirectToAccessDeniedPage() {
        return "component/access-denied";
    }

    @GetMapping("/setting")
    public String redirectToSettingScreen() {
        return "component/setting";
    }
}
