package com.ims_team4.controller;

import com.ims_team4.dto.DepartmentDTO;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.PositionDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.service.*;
import com.ims_team4.utils.email.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ims_team4.utils.RandomCode.generateSixRandomCodes;

//trang
@Controller
@RequestMapping("/user")
public class UserController {
    private final EmployeeService empSrv;
    private final PositionService positionService;
    private final DepartmentService depSrv;
    private final UserService userService;
    private final EmailService emailService;
    // private final TranslateService tSrv;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public UserController(EmployeeService empSrv, PositionService positionService,
            DepartmentService depSrv, UserService userService,
            // TranslateService tSrv,
            EmailService emailService) {
        this.empSrv = empSrv;
        this.positionService = positionService;
        this.depSrv = depSrv;
        this.userService = userService;
        this.emailService = emailService;
        // this.tSrv = tSrv;
    }

    @GetMapping("/list")
    public String redirectToListScreen(Model model,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "positionId", required = false) Long positionId,
            Locale locale) {
        List<EmployeeDTO> list = empSrv.search(title, positionId);
        List<DepartmentDTO> dept = depSrv.getAllDepartments();
        List<PositionDTO> position = positionService.getAllPosition();
        int employeeCount = list.size();
        int activeCount = 0;
        for (EmployeeDTO employee : list) {
            if (employee.isStatus()) { // Kiểm tra nếu status = true
                activeCount++;
            }
        }

        model.addAttribute("position", position);
        model.addAttribute("employees", list);
        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("inactiveCount", (employeeCount-activeCount));
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
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            @RequestParam(value = "note", required = false) String note,
            Model model) {
        try {
            boolean hasErrors = false;

            // Kiểm tra email trùng lặp
            if (userService.existsByEmail(email)) {
                model.addAttribute("emailError", "Email is already registered.");
                hasErrors = true;
            }

            // Kiểm tra số điện thoại trùng lặp
            if (userService.existsByPhone(phoneNumber)) {
                model.addAttribute("phoneError", "Phone number is already registered.");
                hasErrors = true;
            }

            // Kiểm tra tuổi >= 18
            if (dob != null && dob.isAfter(LocalDate.now().minusYears(18))) {
                model.addAttribute("dobError", "Age must be over 18.");
                hasErrors = true;
            }

            if (hasErrors) {
                model.addAttribute("position", positionService.getAllPosition());
                model.addAttribute("dept", depSrv.getAllDepartments());
                return "admin-features/create-user"; // Giữ lại trang với dữ liệu và lỗi
            }

            // Tạo đối tượng UserDTO
            UserDTO userDTO = new UserDTO();
            userDTO.setFullname(fullName);
            userDTO.setDob(dob);
            userDTO.setPhone(phoneNumber);
            userDTO.setAddress(address);
            userDTO.setEmail(email);
            userDTO.setGender(gender);
            boolean isActive = "active".equalsIgnoreCase(status);
            if (avatar != null && !avatar.isEmpty()) {
                userDTO.setAvatar(avatar.getBytes());
            }
            userDTO.setStatus(isActive);
            userDTO.setNote(note);

            // Lưu UserDTO vào database
            UserDTO users = userService.saveUser(userDTO);

            if (users == null || users.getId() == null) {
                log.severe("User creation failed: UserDTO is null or missing ID!");
                model.addAttribute("error", "User creation failed. Please try again.");
                return "admin-features/create-user";
            }

            String password = generateSixRandomCodes();

            // Tạo EmployeeDTO
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setPassword(password);
            employeeDTO.setUserID(users.getId());
            employeeDTO.setPositionId(positionId);
            employeeDTO.setRole(HrRole.valueOf(role));
            employeeDTO.setDepartmentId(departmentId);

            // Lưu Employee vào database
            empSrv.saveEmployee(employeeDTO, employeeDTO.getUserID(), departmentId, positionId);
            sendEmail("ngothutrang1225@gmail.com", password);

            return "redirect:/user/list"; // Thành công -> chuyển hướng danh sách user
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            model.addAttribute("error", "Failed to create employee. Please check your input!");
            return "admin-features/create-user";
        }
    }

    @GetMapping("/create")
    public String redirectToCreateUserScreen(Model model) {
        List<PositionDTO> position = positionService.getAllPosition();
        List<DepartmentDTO> dept = depSrv.getAllDepartments();

        model.addAttribute("position", position);
        model.addAttribute("dept", dept);
        return "admin-features/create-user";
    }

    private void sendEmail(String email, String password) {
        String content = String.format("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Your IMS Account</title>
                </head>
                <body>
                    <p>Dear User,</p>
                    <p>Your new IMS account has been created successfully. Below are your login details:</p>
                    <ul>
                        <li><strong>Username:</strong> %s</li>
                        <li><strong>Password:</strong> %s</li>
                    </ul>
                    <p>Please log in to IMS using your account.</p>
                    <p>For security reasons, please change your password after your first login.</p>
                    <br/>
                    <p>Thanks & Regards,</p>
                    <p><strong>IMS Team</strong></p>
                </body>
                </html>
                """, email, password);

        String subject = "Your IMS Account Details";
        log.info("Sending email with subject: " + subject);
        emailService.sendNormalEmail(email, subject, content);
        log.info("Email has been sent successfully.");
    }

    @GetMapping("/details/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model) {
        EmployeeDTO employee = empSrv.getEmployeeById(id);
        UserDTO user = userService.getUserWithId(id);
        String department = employee.getDepartmentName();
        String position = employee.getPositionName();
        String avatarBase64 = "";
        if (user.getAvatar() != null && user.getAvatar().length > 0) {
            avatarBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(user.getAvatar());
        }
        model.addAttribute("department", department);
        model.addAttribute("employee", user);
        model.addAttribute("position", position);
        model.addAttribute("avatarBase64", avatarBase64);
        return "admin-features/employee-details";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        EmployeeDTO employee = empSrv.getEmployeeById(id);
        UserDTO user = userService.getUserWithId(id);
        log.info("User Data: " + employee);
        String avatarBase64 = "";
        if (user.getAvatar() != null && user.getAvatar().length > 0) {
            avatarBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(user.getAvatar());
        }
        List<PositionDTO> position = positionService.getAllPosition();
        List<DepartmentDTO> dept = depSrv.getAllDepartments();

        model.addAttribute("position", position);
        model.addAttribute("employee", employee);
        model.addAttribute("user", user);
        model.addAttribute("dept", dept);
        model.addAttribute("avatarBase64", avatarBase64);
        return "admin-features/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@RequestParam("fullName") String fullName,
            @PathVariable("id") long id,
            @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("gender") int gender,
            @RequestParam("positionId") Long positionId,
            @RequestParam("role") String role,
            @RequestParam("status") String status,
            @RequestParam("department") Long departmentId,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            @RequestParam(value = "note", required = false) String note,
            Model model) {
        try {
            // Tạo đối tượng UserDTO
            UserDTO userDTO = userService.getUserWithId(id);
            if (userDTO == null) {
                throw new RuntimeException("User not found with ID: " + id); // Hoặc chuyển hướng về trang lỗi
            }
            boolean hasErrors = false;
            // Kiểm tra email trùng lặp
            if (userService.existsByEmail(email)) {
                model.addAttribute("emailError", "Email is already registered.");
                hasErrors = true;
            }
            // Kiểm tra số điện thoại trùng lặp
            if (userService.existsByPhone(phoneNumber)) {
                model.addAttribute("phoneError", "Phone number is already registered.");
                hasErrors = true;
            }
            // Kiểm tra tuổi >= 18
            if (dob != null && dob.isAfter(LocalDate.now().minusYears(18))) {
                model.addAttribute("dobError", "Age must be over 18.");
                hasErrors = true;
            }
            if (hasErrors) {
                EmployeeDTO employee = empSrv.getEmployeeById(id);
                UserDTO user = userService.getUserWithId(id);
                String avatarBase64 = "";
                if (user.getAvatar() != null && user.getAvatar().length > 0) {
                    avatarBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(user.getAvatar());
                }
                List<PositionDTO> position = positionService.getAllPosition();
                List<DepartmentDTO> dept = depSrv.getAllDepartments();

                model.addAttribute("position", position);
                model.addAttribute("employee", employee);
                model.addAttribute("user", user);
                model.addAttribute("dept", dept);
                model.addAttribute("avatarBase64", avatarBase64);
                return "admin-features/edit-user";
            }
            userDTO.setFullname(fullName);
            userDTO.setDob(dob); // Không cần parse nữa
            userDTO.setPhone(phoneNumber);
            userDTO.setAddress(address);
            userDTO.setEmail(email);
            userDTO.setGender(gender);
            boolean isActive = "active".equalsIgnoreCase(status);
            userDTO.setStatus(isActive);
            if (avatar != null && !avatar.isEmpty()) {
                userDTO.setAvatar(avatar.getBytes());
            }
            userDTO.setNote(note);

            // Lưu UserDTO vào database
            userDTO = userService.saveUser(userDTO);

            // Tạo EmployeeDTO sau khi đã có userId
            EmployeeDTO employeeDTO = empSrv.getEmployeeById(id);
            employeeDTO.setUserID(id);
            employeeDTO.setPositionId(positionId);
            employeeDTO.setRole(HrRole.valueOf(role));
            employeeDTO.setDepartmentId(departmentId);
            // Lưu Employee vào database
            empSrv.saveEmployee(employeeDTO, id, departmentId, positionId);
            // Nếu thành công, chuyển hướng về danh sách user
            return "redirect:/user/details/{id}";
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            System.out.println(e.getMessage());
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

    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable Long id, RedirectAttributes attributes) {
        if (userService.getUserWithId(id) != null) {
            empSrv.deleteById(id);
            // userService.deleteById(id);
            attributes.addFlashAttribute("message", "User deleted successfully!");
        } else {
            attributes.addFlashAttribute("error", "User not found!");
        }
        return new RedirectView("/user/list");
    }
    @GetMapping("/test")
    public String testProfile(HttpSession session, Model model) {
        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("account");
        if (employeeDTO == null) {
            log.info("EmployeeDTO is not found in session!");
        } else {
            log.info("EmployeeDTO: " + employeeDTO.toString());
        }
        UserDTO userDTO = userService.getUserWithId(employeeDTO.getUserID());
        if (userDTO == null || employeeDTO == null) {
            return "redirect:/login"; // Hoặc trả về lỗi session timeout
        }
        String avatarBase64 = "";
        if (userDTO.getAvatar() != null && userDTO.getAvatar().length > 0) {
            avatarBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(userDTO.getAvatar());
        }
        // Gán lại vào model để hiển thị trên view
        model.addAttribute("user", userDTO);
        model.addAttribute("employee", employeeDTO);
        String departmentName = employeeDTO.getDepartmentName();
        String positionName = employeeDTO.getPositionName();
        model.addAttribute("avatarBase64", avatarBase64);
        model.addAttribute("departmentName", departmentName);
        model.addAttribute("positionName", positionName);

        return "admin-features/profile"; // Đây là view mà bạn muốn trả về
    }
}