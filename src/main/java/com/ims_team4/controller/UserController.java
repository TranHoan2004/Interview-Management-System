package com.ims_team4.controller;

import com.ims_team4.controller.utils.SessionController;
import com.ims_team4.dto.DepartmentDTO;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.PositionDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.service.DepartmentService;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.PositionService;
import com.ims_team4.service.UserService;
import com.ims_team4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final SessionController details;
    private final EmployeeService empSrv;
    private final PositionService positionService;
    private final DepartmentService depSrv;
    private final UserService userService;


    public UserController(UserServiceImpl impl, EmployeeService empSrv, PositionService positionService,
                          DepartmentService depSrv, UserService userService) {
        this.details = new SessionController(empSrv, impl);;
        this.empSrv = empSrv;
        this.positionService = positionService;
        this.depSrv = depSrv;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String redirectToListScreen(Model model, HttpSession session) {
        List<EmployeeDTO> list = empSrv.getAllEmployee();
        List<DepartmentDTO> dept = depSrv.getAllDepartments();
        List<PositionDTO> position = positionService.getAllPosition();
        model.addAttribute("role", position);
        model.addAttribute("employees", list);
        model.addAttribute("dept", dept);
        details.throwDataToTopSidebar(session, model);
        return "admin-features/list-user";
    }




    @PostMapping("/create")
    public String createUser(@RequestParam("fullName") String fullName,
                             @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                             @RequestParam("phoneNumber") String phoneNumber,
                             @RequestParam("email") String email,
                             @RequestParam("gender") int gender,
                             @RequestParam("positionId") Long positionId,
                             @RequestParam("role") String role,
                             @RequestParam("status") String status,
                             @RequestParam("department") Long departmentId,
                             @RequestParam(value = "note", required = false) String note,
                             Model model) {
        try {
            // Tạo đối tượng UserDTO
            UserDTO userDTO = new UserDTO();
            userDTO.setFullname(fullName);
            userDTO.setDob(dob); // Không cần parse nữa
            userDTO.setPhone(phoneNumber);
            userDTO.setEmail(email);
            userDTO.setGender(gender);
            boolean isActive = "active".equalsIgnoreCase(status);
            userDTO.setStatus(isActive);
            userDTO.setNote(note);

            // Lưu UserDTO vào database
            UserDTO savedUser = userService.saveUser(userDTO);

            // Tạo EmployeeDTO sau khi đã có userId
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setUser(savedUser.getId());
            employeeDTO.setPositionId(positionId);
            employeeDTO.setRole(HrRole.valueOf(role));
            employeeDTO.setDepartmentId(departmentId);

            // Lưu Employee vào database
            empSrv.saveEmployee(employeeDTO);

            // Nếu thành công, chuyển hướng về danh sách user
            return "redirect:/user/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to create employee. Please check your input!");
            return "admin-features/create-user"; // Quay lại trang tạo user nếu có lỗi
        }
    }





    @GetMapping("/create")
    public String redirectToCreateUserScreen(Model model, HttpSession session) {
        details.throwDataToTopSidebar(session, model);
        List<PositionDTO> position = positionService.getAllPosition();
        model.addAttribute("position", position);
        List<DepartmentDTO> dept = depSrv.getAllDepartments();
        model.addAttribute("dept", dept);
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