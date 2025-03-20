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
import org.jboss.logging.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

//trang
@Controller
@RequestMapping("/user")
public class UserController {
    private final SessionController details;
    private final EmployeeService empSrv;
    private final PositionService positionService;
    private final DepartmentService depSrv;
    private final UserService userService;
    private final Logger log = Logger.getLogger(this.getClass().getName());


    public UserController(UserServiceImpl impl, EmployeeService empSrv, PositionService positionService,
                          DepartmentService depSrv, UserService userService) {
        this.details = new SessionController(empSrv, impl);
        ;
        this.empSrv = empSrv;
        this.positionService = positionService;
        this.depSrv = depSrv;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String redirectToListScreen(Model model, HttpSession session,
                                       @RequestParam(name = "title", required = false) String title,
                                       @RequestParam(name = "positionId", required = false) Long positionId) {
        List<EmployeeDTO> list = empSrv.search(title, positionId);
        List<DepartmentDTO> dept = depSrv.getAllDepartments();
        List<PositionDTO> position = positionService.getAllPosition();
        model.addAttribute("position", position);
        model.addAttribute("employees", list);
        model.addAttribute("dept", dept);

        log.info("redirectToListScreen: " + positionId);
        return "admin-features/list-user";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam("fullName") String fullName,
                             @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                             @RequestParam("phoneNumber") String phoneNumber,
                             @RequestParam("address") String address,
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
            userDTO.setAddress(address);
            userDTO.setEmail(email);
            userDTO.setGender(gender);
            boolean isActive = "active".equalsIgnoreCase(status);
            userDTO.setStatus(isActive);
            userDTO.setNote(note);

            // Lưu UserDTO vào database
            UserDTO savedUser = userService.saveUser(userDTO);

            // Tạo EmployeeDTO sau khi đã có userId
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setUserID(savedUser.getId());
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
        List<PositionDTO> position = positionService.getAllPosition();
        model.addAttribute("position", position);
        List<DepartmentDTO> dept = depSrv.getAllDepartments();
        model.addAttribute("dept", dept);
        return "admin-features/create-user";
    }

    @GetMapping("/details/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model) {
        EmployeeDTO employee = empSrv.getEmployeeById(id);
        UserDTO user = userService.getUserWithId(id);
        String department = employee.getDepartmentName();
        String position = employee.getPositionName();

        model.addAttribute("department", department);
        model.addAttribute("employee", user);
        model.addAttribute("position", position);
        return "admin-features/employee-details";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model, HttpSession session) {
        EmployeeDTO employee = empSrv.getEmployeeById(id);
        UserDTO user = userService.getUserWithId(id);
        model.addAttribute("employee", employee);
        model.addAttribute("user", user);
        System.out.println("User Data: " + employee);
        List<PositionDTO> position = positionService.getAllPosition();
        model.addAttribute("position", position);
        List<DepartmentDTO> dept = depSrv.getAllDepartments();
        model.addAttribute("dept", dept);
        return "admin-features/edit-user";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam("fullName") String fullName,
                           @RequestParam("id") long id,
                           @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                           @RequestParam("phoneNumber") String phoneNumber,
                           @RequestParam("email") String email,
                           @RequestParam("address") String address,
                           @RequestParam("gender") int gender,
                           @RequestParam("positionId") Long positionId,
                           @RequestParam("role") String role,
                           @RequestParam("status") String status,
                           @RequestParam("department") Long departmentId,
                           @RequestParam(value = "note", required = false) String note,
                           Model model) {
        try {
            // Tạo đối tượng UserDTO
            UserDTO userDTO = userService.getUserWithId(id);
            if (userDTO == null) {
                throw new RuntimeException("User not found with ID: " + id); // Hoặc chuyển hướng về trang lỗi
            }
            userDTO.setFullname(fullName);
            userDTO.setDob(dob); // Không cần parse nữa
            userDTO.setPhone(phoneNumber);
            userDTO.setAddress(address);
            userDTO.setEmail(email);
            userDTO.setGender(gender);
            boolean isActive = "active".equalsIgnoreCase(status);
            userDTO.setStatus(isActive);
            userDTO.setNote(note);

            // Lưu UserDTO vào database
            UserDTO savedUser = userService.saveUser(userDTO);

            // Tạo EmployeeDTO sau khi đã có userId
            EmployeeDTO employeeDTO = empSrv.getEmployeeById(id);
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
            return "admin-features/edit-user"; // Quay lại trang tạo user nếu có lỗi
        }
    }

    @PostMapping("/activate/{id}")
    public String toggleUserStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        UserDTO user = userService.getUserWithId(id);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found!");
            return "redirect:/user/list"; // Nếu không tìm thấy user, quay lại danh sách
        }

        user.setStatus(!user.isStatus()); // Đảo trạng thái Active/Inactive
        userService.saveUser(user);

        redirectAttributes.addFlashAttribute("success", "User status updated successfully!");

        // 🛠 Đảm bảo redirect về trang Employee Details
        return "redirect:/user/details/" + id;
    }

}