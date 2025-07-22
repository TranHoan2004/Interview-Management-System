package com.ims_team4.controller;

import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.HighestLevelDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.*;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.repository.UserRepository;
import com.ims_team4.service.*;
import com.ims_team4.service.impl.CandidateServiceImpl;
import com.ims_team4.utils.email.EmailService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/candidate")
// Dang Momo
public class CandidateController {
    private final CandidateService candidateService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private final HighestLevelService highestLevelService;
    private final PositionService positionService;
    private final SkillService skillService;
    private final CandidateServiceImpl candidateServiceImpl;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public CandidateController(CandidateService candidateService, EmployeeService employeeService,
                               UserService userService, HighestLevelService highestLevelService, PositionService positionService,
                               SkillService skillService, CandidateServiceImpl candidateServiceImpl, UserRepository userRepository,
                               EmailService emailService) {
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.highestLevelService = highestLevelService;
        this.positionService = positionService;
        this.skillService = skillService;
        this.candidateServiceImpl = candidateServiceImpl;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    /**
     * UC05: View Candidates List. GET /candidate/list
     */
    @GetMapping("/list")
    public String viewCandidateList(Model model, Locale locale) {
        model.addAttribute("candidates", candidateService.getAllCandidate2());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                model.addAttribute("user", principal);
            }
        }

        // Tính toán giá trị của các phần tử cần thiết
        int totalCandidates = candidateService.getTotalCandidates();
        int openCandidates = candidateService.getOpenCandidates();
        int waitingForInterviewCandidates = candidateService.getWaitingForInterviewCandidates();
        int passedInterviewCandidates = candidateService.getPassedInterviewCandidates();

        // Cập nhật giá trị của các phần tử stat-card-value
        model.addAttribute("totalCandidates", totalCandidates);
        model.addAttribute("openCandidates", openCandidates);
        model.addAttribute("waitingForInterviewCandidates", waitingForInterviewCandidates);
        model.addAttribute("passedInterviewCandidates", passedInterviewCandidates);

        model.addAttribute("language", locale);
        return "Candidate/candidate-list";
    }

    /**
     * UC07: View candidate details. GET /candidate/details
     */
    @GetMapping("/details/{userId}")
    public String getCandidateDetails(@PathVariable Long userId, Model model) {
        CandidateDTO candidate = candidateService.getCandidateById2(userId);

        if (candidate == null) {
            throw new RuntimeException("Candidate not found");
        }
        // ✅ Gọi đúng phương thức từ Service (Lấy danh sách DTO thay vì Entity)
        List<HighestLevelDTO> highestLevels = highestLevelService.getAllHighestLevel();
        model.addAttribute("highestLevels", highestLevels); // Truyền vào model

        model.addAttribute("candidate", candidate); // Đúng tên Thymeleaf sử dụng
        return "Candidate/candidate-details";
    }

    // UC08: Create candidate.
    @GetMapping("/add")
    public String showCreateCandidateForm(Model model) {
        // ✅ Gọi đúng phương thức từ Service (Lấy danh sách DTO thay vì Entity)
        List<HighestLevelDTO> highestLevels = highestLevelService.getAllHighestLevel();

        model.addAttribute("highestLevels", highestLevels); // Truyền vào model
        model.addAttribute("skills", skillService.getAllSkill()); // Lấy danh sách kỹ năng từ DB
        model.addAttribute("recruiters", employeeService.getAllEmployeeByRole(HrRole.ROLE_RECRUITER));
        model.addAttribute("positions", positionService.getAllPositions()); // ✅ Gọi phương thức có sẵn
        return "Candidate/create-candidate";
    }

    @PostMapping("/add")
    public String addCandidate(@ModelAttribute CandidateDTO candidateDTO,
                               @RequestParam("cvFile") MultipartFile cvFile,
                               @RequestParam(value = "skills", required = false) List<String> skillNames,
                               Model model, RedirectAttributes redirectAttributes) {
        try {
            // ✅ Kiểm tra email & số điện thoại trùng lặp
            boolean emailExists = userRepository.existsByEmail(candidateDTO.getEmail());
            boolean phoneExists = userRepository.existsByPhone(candidateDTO.getPhone());

            if (emailExists && phoneExists) {
                redirectAttributes.addFlashAttribute("error", "Phone number and Email already exist.");
            } else if (emailExists) {
                redirectAttributes.addFlashAttribute("error", "Email already exists.");
            } else if (phoneExists) {
                redirectAttributes.addFlashAttribute("error", "Phone number already exists.");
            }

            if (emailExists || phoneExists) {
                return "redirect:/candidate/add";
            }

            // ✅ Kiểm tra file CV
            if (cvFile.isEmpty()) {
                model.addAttribute("error", "CV file is required!");
                return "Candidate/create-candidate";
            }

            // ✅ Chuyển đổi CV thành mảng byte
            candidateDTO.setCv(cvFile.getBytes());

            // ✅ Lấy thông tin học vấn & vị trí
            Optional<HighestLevel> highestLevelOpt = highestLevelService
                    .getHighestLevelById(candidateDTO.getHighestEducation());
            Position position = positionService.getPositionById((long) candidateDTO.getPositionId());

            if (highestLevelOpt.isEmpty()) {
                model.addAttribute("error", "Highest Level not found!");
                return "Candidate/create-candidate";
            }

            if (position == null) {
                model.addAttribute("error", "Position not found!");
                return "Candidate/create-candidate";
            }

            // ✅ Tạo user cho candidate
            Users createdUser = userService.createUser(getUserDTOs(candidateDTO));
            if (createdUser == null) {
                model.addAttribute("error", "Error: Failed to create user.");
                return "Candidate/create-candidate";
            }

            candidateDTO.setUserId(createdUser.getId());

            // ✅ Lấy Recruiter
            Employee assignedRecruiter;
            if (candidateDTO.getRecruiterId() != null) {
                Long recruiterUserId = candidateDTO.getRecruiterId();
                assignedRecruiter = employeeService.getEmployeeByUserId(recruiterUserId)
                        .orElseThrow(
                                () -> new RuntimeException("Recruiter not found with User ID: " + recruiterUserId));
            } else {
                assignedRecruiter = employeeService.getDefaultEmployee()
                        .orElseThrow(() -> new RuntimeException("Default Employee not found!"));
            }

            // ✅ Thêm candidate
            boolean isSaved = candidateService.addCandidate(candidateDTO, highestLevelOpt.get(), position,
                    assignedRecruiter, createdUser);

            if (!isSaved) {
                model.addAttribute("error", "Failed to add candidate.");
                return "Candidate/create-candidate";
            }

            // ✅ Xử lý kỹ năng nếu có
            if (skillNames != null && !skillNames.isEmpty()) {
                Set<Skill> skills = skillService.getSkillsByName(skillNames);
                Optional<Candidate> candidateOpt = candidateServiceImpl.findCandidateByUserId(createdUser.getId());

                if (candidateOpt.isPresent()) {
                    Candidate candidate = candidateOpt.get();
                    candidateService.addSkillsToCandidate(candidate, skills);
                } else {
                    model.addAttribute("error", "Candidate not found for User ID: " + createdUser.getId());
                    return "Candidate/create-candidate";
                }
            }

            // ✅ Thành công → Quay về danh sách
            redirectAttributes.addFlashAttribute("createSuccess", "Candidate added successfully!");
            return "redirect:/candidate/list";

        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            return "Candidate/create-candidate";
        }
    }

    // UC09: Delete candidate
    @Transactional
    @GetMapping("/delete/{userId}")
    public String deleteCandidate(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        CandidateDTO candidate = candidateService.getCandidateDetails(userId);
        try {
            if (candidate == null) {
                throw new Exception("Candidate not found.");
            }
            if (!"OPEN".equalsIgnoreCase(candidate.getStatus())) {
                throw new Exception("Candidate cannot be deleted unless status is 'OPEN'.");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/candidate/list";
        }

        log.info("Proceeding to delete Candidate with userId: {}", userId);
        boolean candidateDeleted = candidateService.deleteCandidateByUserId(userId);
        if (candidateDeleted) {
            log.info("Proceeding to delete User with userId: {}", userId);
            userService.deleteUserById(userId);
            redirectAttributes.addFlashAttribute("success", "Candidate deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error deleting candidate.");
        }
        return "redirect:/candidate/list";
    }

    // UC10: Ban candidate
    @PostMapping("/ban/{userId}")
    public String toggleBanCandidate(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        try {
            CandidateDTO candidate = candidateService.getCandidateDetails(userId);

            if (candidate == null) {
                redirectAttributes.addFlashAttribute("error", "Candidate not found.");
                return "redirect:/candidate/list";
            }

            // Toggle status: OPEN → BANNED, BANNED → OPEN
            boolean isUpdated = candidateService.toggleBanStatus(userId);

            if (isUpdated) {
                if ("BANNED".equals(candidate.getStatus())) {
                    redirectAttributes.addFlashAttribute("unbanSuccess", "Candidate successfully unbanned.");
                } else {
                    redirectAttributes.addFlashAttribute("banSuccess", "Candidate successfully banned.");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to update candidate status.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unexpected error: " + e.getMessage());
        }

        return "redirect:/candidate/list";
    }

    // Download CV at use case candidate details
    @GetMapping("/download-cv/{userId}")
    public ResponseEntity<byte[]> downloadCv(@PathVariable Long userId) {
        CandidateDTO candidate = candidateService.getCandidateById2(userId);

        if (candidate == null || candidate.getCv() == null || candidate.getCv().length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("CV_" + userId + ".pdf").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(candidate.getCv());
    }

    private UserDTO getUserDTOs(@NotNull CandidateDTO candidateDTO) {
        return UserDTO.builder()
                .fullname(candidateDTO.getFullName())
                .email(candidateDTO.getEmail())
                .phone(candidateDTO.getPhone())
                .dob(candidateDTO.getDob())
                .address(candidateDTO.getAddress())
                .gender(candidateDTO.getGender())
                .note(candidateDTO.getNote())
                .status(true)
                .build();
    }

    // UC7: Edit candidate
    @GetMapping("/edit/{userId}")
    public String showEditCandidateForm(@PathVariable Long userId, Model model) {
        CandidateDTO candidate = candidateService.getCandidateById2(userId);
        if (candidate == null) {
            throw new RuntimeException("Candidate not found");
        }

        // ✅ Lấy recruiter hiện tại của Candidate
        long currentRecruiterId = candidate.getRecruiterId() != null ? candidate.getRecruiterId() : -1;

        // ✅ Lọc danh sách Recruiters, loại bỏ recruiter hiện tại
        List<EmployeeDTO> recruiters = employeeService.getAllEmployeeByRole(HrRole.ROLE_RECRUITER)
                .stream()
                .filter(r -> r.getUserID() != currentRecruiterId) // ✅ Đảm bảo không hiển thị recruiter hiện tại
                .toList();

        // ✅ Lấy danh sách trình độ học vấn từ database
        List<HighestLevelDTO> highestLevels = highestLevelService.getAllHighestLevel();

        // ✅ Thêm vào model
        model.addAttribute("highestLevels", highestLevels);
        model.addAttribute("skills", skillService.getAllSkill());
        model.addAttribute("positions", positionService.getAllPositions());
        model.addAttribute("recruiters", recruiters);
        model.addAttribute("candidate", candidate);

        return "Candidate/edit-candidate";
    }

    @PostMapping("/edit")
    public String updateCandidate(@ModelAttribute CandidateDTO candidateDTO,
                                  @RequestParam(value = "cvFile", required = false) MultipartFile cvFile,
                                  RedirectAttributes redirectAttributes, HttpSession session) {
        try {

            boolean emailExists = userRepository.existsByEmailAndUserIdNot(candidateDTO.getEmail(),
                    candidateDTO.getUserId());
            boolean phoneExists = userRepository.existsByPhoneAndUserIdNot(candidateDTO.getPhone(),
                    candidateDTO.getUserId());

            if (emailExists && phoneExists) {
                redirectAttributes.addFlashAttribute("error", "Phone number and Email already exist.");
                redirectAttributes.addFlashAttribute("editCandidateFailed", "Edit candidate failed!");
            } else if (emailExists) {
                redirectAttributes.addFlashAttribute("error", "Email already exists.");
                redirectAttributes.addFlashAttribute("editCandidateFailed", "Edit candidate failed!");
            } else if (phoneExists) {
                redirectAttributes.addFlashAttribute("error", "Phone number already exists.");
                redirectAttributes.addFlashAttribute("editCandidateFailed", "Edit candidate failed!");
            }

            if (emailExists || phoneExists) {
                return "redirect:/candidate/edit/" + candidateDTO.getUserId();
            }

            log.info("\uD83D\uDD04 [UPDATE] Request received for Candidate ID: {}", candidateDTO.getUserId());

            // In ra giá trị recruiterId nhận từ form
            log.info("\uD83D\uDCCC Received Recruiter ID from form: {}", candidateDTO.getRecruiter());

            // Nếu có CV mới, cập nhật vào DTO
            if (cvFile != null && !cvFile.isEmpty()) {
                candidateDTO.setCv(cvFile.getBytes());
                log.info("\uD83D\uDCC2 CV file uploaded: {}", cvFile.getOriginalFilename());
            }

            // Lấy HighestLevel
            Optional<HighestLevel> highestLevelOpt = highestLevelService
                    .getHighestLevelById(candidateDTO.getHighestEducation());
            if (highestLevelOpt.isEmpty()) {
                log.error("❌ Highest Level not found for ID: {}", candidateDTO.getHighestEducation());
                redirectAttributes.addFlashAttribute("error", "Highest Level not found.");
                return "redirect:/candidate/list";
            }
            log.info("✅ Highest Level found: {}", highestLevelOpt.get().getName());

            // Lấy danh sách kỹ năng từ database
            Set<Skill> updatedSkills = new HashSet<>();
            if (candidateDTO.getSkills() != null && !candidateDTO.getSkills().isEmpty()) {
                updatedSkills = skillService.getSkillsByName(new ArrayList<>(candidateDTO.getSkills()));
            }

            // Kiểm tra nếu recruiterId có giá trị hợp lệ
            Employee newRecruiter = null;
            if (candidateDTO.getRecruiter() != null && !candidateDTO.getRecruiter().isEmpty()) {
                Long recruiterUserId = Long.parseLong(candidateDTO.getRecruiter());
                log.info("Fetching recruiter with ID: {}", recruiterUserId);

                newRecruiter = employeeService.getEmployeeByUserId(recruiterUserId)
                        .orElseThrow(() -> new RuntimeException("❌ Recruiter not found with ID: " + recruiterUserId));

                log.info("✅ New Recruiter found: {}", newRecruiter.getUser().getFullname());
            }

            // Lấy User để cập nhật thông tin cá nhân
            Users user = userService.getUser(candidateDTO.getUserId());
            if (user == null) {
                log.error("❌ User not found for Candidate ID: {}", candidateDTO.getUserId());
                redirectAttributes.addFlashAttribute("error", "User not found.");
                return "redirect:/candidate/list";
            }

            // Cập nhật thông tin User
            user.setFullname(candidateDTO.getFullName());
            user.setEmail(candidateDTO.getEmail());
            user.setPhone(candidateDTO.getPhone());
            user.setDob(candidateDTO.getDob());
            user.setAddress(candidateDTO.getAddress());
            user.setGender(candidateDTO.getGender());
            user.setNote(candidateDTO.getNote());

            // Lấy Position mới
            Position newPosition = positionService.getPositionById((long) candidateDTO.getPositionId());

            // Cập nhật Candidate
            boolean isUpdated = candidateService.updateCandidate(
                    candidateDTO, highestLevelOpt.get(), updatedSkills, user, newPosition, newRecruiter);

            if (isUpdated) {
                log.info("✅ [SUCCESS] Candidate updated successfully!");
                redirectAttributes.addFlashAttribute("editSuccess", "Candidate updated successfully!"); // ✅ Thêm thông
                // báo flash

                // ✅ Kiểm tra nếu status được cập nhật thành "Waiting for Interview"
                if ("WAITING_FOR_INTERVIEW".equals(candidateDTO.getStatus())) {
                    log.info("\uD83D\uDCCC Candidate status received from form: {}", candidateDTO.getStatus());
                    redirectAttributes.addFlashAttribute("showInterviewModal", true);
                    log.info("\uD83D\uDCCC showInterviewModal flag set in controller: {}", redirectAttributes.getFlashAttributes().get("showInterviewModal"));
                    redirectAttributes.addFlashAttribute("candidateName", candidateDTO.getFullName());
                    redirectAttributes.addFlashAttribute("candidateUserId", candidateDTO.getUserId());
                    redirectAttributes.addFlashAttribute("jobId", candidateDTO.getPositionId());
                    EmployeeDTO emp = (EmployeeDTO) session.getAttribute("account");
                    redirectAttributes.addFlashAttribute("userId", emp.getUserID());
                }
                return "redirect:/candidate/list"; // Trả về trang list để hiển thị thông báo "Edit thành công"
            }

        } catch (Exception e) {
            log.error("❌ Unexpected error: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Unexpected error: " + e.getMessage());
        }

        return "redirect:/candidate/list";
    }

    @PostMapping("send-invitation/{userId}")
    public String sendInterviewInvitation(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        Optional<Users> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("emailStatus", "error");
            redirectAttributes.addFlashAttribute("emailMessage", "Không tìm thấy người dùng!");
            return "redirect:/candidate/list"; // Chuyển hướng về Candidate List
        }

        Optional<Candidate> candidateOpt = candidateService.findCandidateByUserId(userId);
        if (candidateOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("emailStatus", "error");
            redirectAttributes.addFlashAttribute("emailMessage", "Không tìm thấy ứng viên!");
            return "redirect:/candidate/list";
        }

        Candidate candidate = candidateOpt.get();
        String email = userOpt.get().getEmail();
        String candidateName = candidate.getUser().getFullname();
        String subject = "Thư Mời Phỏng Vấn từ FPT Software";
        String content = getEmailContent(candidateName);

        try {
            emailService.sendNormalEmail(email, subject, content);
            redirectAttributes.addFlashAttribute("emailStatus", "success");
            redirectAttributes.addFlashAttribute("emailMessage", "Interview invitation sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("emailStatus", "error");
            redirectAttributes.addFlashAttribute("emailMessage",
                    "Failed to send interview invitation! Error: " + e.getMessage());
        }
        redirectAttributes.addFlashAttribute("showInterviewModal", true);

        return "redirect:/candidate/list"; // Sau khi gửi xong, quay lại trang Candidate List
    }

    // Nội dung email mời phỏng vấn
    @NotNull
    @Contract(pure = true)
    private String getEmailContent(String candidateName) {
        return String.format(
                """
                        <!DOCTYPE html>
                        <html lang="vi">
                        <head>
                            <meta charset="UTF-8">
                            <title>Thư Mời Phỏng Vấn</title>
                        </head>
                        <body style="font-family: Arial, sans-serif; line-height: 1.6; margin: 20px; padding: 20px;">
                            <h2 style="color: #0056b3; text-align: center;">THƯ MỜI THAM GIA BUỔI PHỎNG VẤN</h2>
                        
                            <p><b>Thân gửi %s,</b></p>
                        
                            <p>Lời đầu tiên, chúng tôi xin trân trọng cảm ơn bạn đã quan tâm đến cơ hội làm việc tại FPT Software.</p>
                        
                            <p>Sau khi xem xét hồ sơ của bạn, Phòng Tuyển dụng FPT Software trân trọng mời bạn tham dự buổi phỏng vấn tuyển dụng để trao đổi thêm về vị trí công việc phù hợp.</p>
                        
                            <p><b>Thông tin buổi phỏng vấn của bạn như sau:</b></p>
                            <ul>
                                <li><b>Thời gian:</b> Công ty sẽ sắp xếp lịch phỏng vấn và thông báo cho bạn qua email.</li>
                                <li><b>Địa điểm:</b> Tòa nhà FPT Software Academy (Hòa Lạc)</li>
                                <li><b>Người hỗ trợ:</b> Mr Minh Chuyên – 0389 289 922</li>
                            </ul>
                        
                            <p><b>Lưu ý:</b></p>
                            <ul>
                                <li>Theo dõi email để nhận thông tin chi tiết về thời gian và hình thức phỏng vấn.</li>
                                <li>Tham gia phỏng vấn đầy đủ, đúng giờ (tới trước ít nhất 15 phút).</li>
                                <li>Chuẩn bị kiến thức và tinh thần tốt trước khi tham gia phỏng vấn.</li>
                            </ul>
                        
                            <p>Chúng tôi hy vọng sẽ có cơ hội làm việc cùng bạn trong thời gian tới!</p>
                        
                            <p>Trân trọng cảm ơn!</p>
                        
                            <hr>
                        
                            <p><b>Trân trọng thông báo,</b></p>
                            <p><b>Công ty TNHH Phần mềm FPT</b></p>
                        
                            <p>
                                <b>MINH CHUYÊN (HUYENNV2)</b><br>
                                FPT Software Academy<br>
                                9th Floor, FPT Building, Duy Tan Road, Cau Giay Dist, Hanoi<br>
                                Tel: +84 389 289 922 | Facebook: minhchuyn<br>
                                Website: <a href="https://fsoft-academy.edu.vn">fsoft-academy.edu.vn</a>
                            </p>
                        </body>
                        </html>
                        """,
                candidateName);
    }
}
