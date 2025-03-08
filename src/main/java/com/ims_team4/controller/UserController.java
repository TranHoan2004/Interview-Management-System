package com.ims_team4.controller;

import com.ims_team4.controller.utils.SessionController;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.UserService;
import com.ims_team4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final SessionController details;
    private final EmployeeService empSrv;

    public UserController(UserServiceImpl impl, EmployeeService empSrv) {
        this.details = new SessionController(empSrv, impl);;
        this.empSrv = empSrv;
    }

    @GetMapping("/list")
    public String redirectToListScreen(Model model, HttpSession session) {
        List<EmployeeDTO> list = empSrv.getAllEmployee();
        model.addAttribute("employees", list);
        details.throwDataToTopSidebar(session, model);
        return "admin-features/list-user";
    }

    @GetMapping("/create")
    public String redirectToCreateUserScreen(Model model, HttpSession session) {
        details.throwDataToTopSidebar(session, model);
        return "admin-features/create-user";
    }

    @GetMapping("/details")
    public String viewUser(@RequestParam(name = "id") Long id) {
        System.out.println(id);
        return "index";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam(name = "id") Long id) {
        System.out.println(id);
        return "index";
    }
}
