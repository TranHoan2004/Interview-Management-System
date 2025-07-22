package com.ims_team4.controller;

import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.dto.NotificationDTO;
import com.ims_team4.model.Candidate;
import com.ims_team4.model.Employee;
import com.ims_team4.model.Interview;
import com.ims_team4.model.Job;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.model.utils.InterviewStatus;
import com.ims_team4.repository.CandidateRepository;
import com.ims_team4.repository.EmployeeRepository;
import com.ims_team4.repository.JobRepository;
import com.ims_team4.service.CandidateService;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.InterviewService;
import com.ims_team4.utils.email.EmailService;
import com.ims_team4.utils.email.EmailServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * InterviewController handles requests related to Interview functionality: - UC16 list schedules - UC18 view details -
 * UC17 create schedule - UC20 edit schedule - UC21 cancel schedule - UC19 submit result - UC22 send reminder
 */
@Slf4j
@Controller
@RequestMapping("/interview")
@EnableScheduling
// Vu, HoanTX
public class InterviewController {
    private final InterviewService interviewService;
    private final EmployeeService empSrv;
    private final CandidateService cSrv;
    private final CandidateRepository candidateRepository;
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;
    private final String INTERVIEW_LINK = "http://localhost:8080/interview";

    public InterviewController(InterviewService interviewService, EmployeeService empSrv,
                               CandidateService cSrv, CandidateRepository candidateRepository,
                               EmployeeRepository employeeRepository,
                               JobRepository jobRepository) {
        this.interviewService = interviewService;
        this.empSrv = empSrv;
        this.cSrv = cSrv;
        this.candidateRepository = candidateRepository;
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
    }

    // UC16: View (search) interview schedules. GET /interview/list
    @GetMapping("/list")
    public String getInterviewList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) InterviewStatus status,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<InterviewDTO> interviewPage = interviewService.searchInterviews(search, status, employeeId, page, size);

        List<EmployeeDTO> interviewers = empSrv.getAllEmployeeByRole(HrRole.ROLE_INTERVIEWER);

        model.addAttribute("interviewPage", interviewPage);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("interviewers", interviewers);

        model.addAttribute("id_label", "ID");
        model.addAttribute("title_label", "Title");
        model.addAttribute("candidate", "Candidate");
        model.addAttribute("interviewerTitle", "Interviewer");
        model.addAttribute("schedule_time_title", "Schedule Time");
        model.addAttribute("location_label", "Locations");
        model.addAttribute("job_title", "Job Title");
        model.addAttribute("result_title", "Result");
        model.addAttribute("statusTitle", "Status");
        model.addAttribute("action", "Action");

        model.addAttribute("new_title", "NEW");
        model.addAttribute("invited_title", "INVITED");
        model.addAttribute("interviewed_title", "INTERVIEWED");
        model.addAttribute("canceled_title", "CANCELLED");

        return "Interview/list";
    }

    // UC18: View interview schedule details . GET /interview/interviewDetail?id=xxx
    @GetMapping("/interviewDetail")
    public String getInterviewDetail(@RequestParam Long id,
                                     @RequestParam(name = "success", required = false, defaultValue = "false")
                                     boolean success,
                                     Model model) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        model.addAttribute("interview", interview);
        model.addAttribute("success", success);

        model.addAttribute("new_title", "NEW");
        model.addAttribute("invited_title", "INVITED");
        model.addAttribute("interviewed_title", "INTERVIEWED");
        model.addAttribute("canceled_title", "CANCELLED");
        return "Interview/detail";
    }

    // <editor-fold> desc="UC17: Create new interview schedule"
    //UC17: Create new interview schedule. GET /interview/createInterviewView
    @GetMapping("/createInterviewView")
    public String showCreateInterviewForm(Model model, @RequestParam(required = false) Long candidateId,
                                          @RequestParam(required = false) Long jobId) {
        InterviewDTO data = new InterviewDTO();
        if (candidateId != null) {
            data.setCandidateId(candidateId);
            data.setJobId(jobId);
        }
        model.addAttribute("interviewForm", data);
        return "Interview/create";
    }

    @PostMapping("/createInterview")
    public String createInterview(@ModelAttribute("interviewForm") InterviewDTO interviewDTO) {
        // 1) Fetch the needed Entities from their repositories
        Candidate candidate = candidateRepository.findByUserId(interviewDTO.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found!"));

        Job job = null;
        if (interviewDTO.getJobId() != null) {
            job = jobRepository.findById(interviewDTO.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found!"));
        }

        Set<Employee> employees = new HashSet<>();
        if (interviewDTO.getEmployeeIds() != null) {
            employees = interviewDTO.getEmployeeIds().stream()
                    .map(empId -> employeeRepository.findById(empId)
                            .orElseThrow(() -> new RuntimeException("Employee not found: " + empId)))
                    .collect(Collectors.toSet());
        }

        Interview interview = new Interview();
        interview.setTitle(interviewDTO.getTitle());
        interview.setNote(interviewDTO.getNote());
        interview.setMeetId(interviewDTO.getMeetId());
        interview.setScheduleTime(interviewDTO.getScheduleTime());
        interview.setStartTime(interviewDTO.getStartTime());
        interview.setEndTime(interviewDTO.getEndTime());
        interview.setStatus(InterviewStatus.NEW);
        interview.setLocations(interviewDTO.getLocations());
        interview.setResult(interviewDTO.getResult());
        interview.setCandidate(candidate);
        interview.setRecruiterOwner(interviewDTO.getRecruiterOwner());
        interview.setJob(job);
        interview.setNotificationSent(false);
        interview.setEmployees(employees);

        interviewService.createInterview(interview);

        return "redirect:/interview/list";
    }

    // </editor-fold>

    // <editor-fold> desc="UC20: Edit interview details."
    // GET /interview/editInterviewView?id=xxx -> Show the edit form
    @GetMapping("/editInterviewView")
    public String showEditInterviewForm(@RequestParam Long id, Model model) {
        InterviewDTO interviewDTO = interviewService.getInterviewById(id);
        model.addAttribute("interviewEdit", interviewDTO);
        return "Interview/edit";
    }

    // PUT/POST /interview/editInterview -> Actually update
    @PostMapping("/editInterview")
    public String editInterview(@ModelAttribute("interviewEdit") InterviewDTO interviewDTO) {
        // Load the existing Interview entity from DB
        Interview existing = interviewService.findEntityById(interviewDTO.getId());

        // candidate
        if (interviewDTO.getCandidateId() != 0
                && (existing.getCandidate() == null
                || !existing.getCandidate().getId().equals(interviewDTO.getCandidateId()))) {
            Candidate candidate = candidateRepository.findById(interviewDTO.getCandidateId())
                    .orElseThrow(() -> new RuntimeException("Candidate not found!"));
            existing.setCandidate(candidate);
        }

        // job
        if (interviewDTO.getJobId() != null
                && (existing.getJob() == null
                || !existing.getJob().getId().equals(interviewDTO.getJobId()))) {
            Job job = jobRepository.findById(interviewDTO.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found!"));
            existing.setJob(job);
        }

        if (interviewDTO.getEmployeeIds() != null) {
            Set<Employee> newEmployees = interviewDTO.getEmployeeIds().stream()
                    .map(empId -> employeeRepository.findById(empId)
                            .orElseThrow(() -> new RuntimeException("Employee not found: " + empId)))
                    .collect(Collectors.toSet());
            existing.setEmployees(newEmployees);
        }

        existing.setTitle(interviewDTO.getTitle());
        existing.setNote(interviewDTO.getNote());
        existing.setMeetId(interviewDTO.getMeetId());
        existing.setScheduleTime(interviewDTO.getScheduleTime());
        existing.setStartTime(interviewDTO.getStartTime());
        existing.setEndTime(interviewDTO.getEndTime());
        existing.setLocations(interviewDTO.getLocations());
        existing.setResult(interviewDTO.getResult());
        existing.setRecruiterOwner(interviewDTO.getRecruiterOwner());

        interviewService.updateInterview(existing);

        return "redirect:/interview/list";
    }
    // </editor-fold>

    // UC21: Cancel interview schedule (HTML approach). GET /interview/cancelInterview?id=xxx
    @GetMapping("/cancelInterview")
    public String cancelInterview(@RequestParam Long id) {
        interviewService.cancelInterview(id);
        return "redirect:/interview/list";
    }

    // <editor-fold> desc="UC19: Submit interview result (HTML approach)."
    // GET /interview/submitResultView?id=xxx -> Show a form
    @GetMapping("/submitResultView")
    public String showSubmitResultForm(@RequestParam Long id, Model model) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        model.addAttribute("interview", interview);
        return "Interview/submit-result";
    }

    // POST /interview/submitResult -> Actually submit
    @PostMapping("/submitResult")
    public String submitInterviewResult(@RequestParam Long interviewId,
                                        @RequestParam String result) {
        interviewService.submitInterviewResult(interviewId, result);
        return "redirect:/interview/list";
    }
    // </editor-fold>

    // <editor-fold> desc="UC22: Send a reminder for upcoming schedule (HTML approach). For a quick approach, we might just do a GET or POST link."

    /**
     * <p>Send API status to detail screen value of true for send a reminder successfully.</p>
     * <p>Look at index.html and /index in HomeController to see how to catch data throw from this below method.</p>
     */
    // HoanTX, VuTD
    @GetMapping("/reminder")
    @ResponseBody
    public ResponseEntity<?> sendReminder(@RequestParam Long interviewId, HttpServletRequest request, Model model) {
        // Lấy dữ liệu interview, candidate, recruiter (employee)
        InterviewDTO interviewDTO = interviewService.getInterviewById(interviewId);
        // Giả sử cSrv.getCandidateByUserId trả về list, ta lấy phần tử đầu tiên
        CandidateDTO candidateDTO = cSrv.getCandidateByUserId(interviewDTO.getCandidateId()).getFirst();
        EmployeeDTO employeeDTO = empSrv.getEmployeeById(interviewDTO.getRecruiterOwner());

        // Xây dựng URL để chuyển hướng (dùng trong thông báo)
        String url = INTERVIEW_LINK + "/interviewDetail?id=" + interviewDTO.getId();
        String content = writeBodyContent(interviewDTO, candidateDTO, employeeDTO.getEmail(), url);

        // Gửi email (hàm sendEmail cũ)
        sendEmail(interviewDTO, candidateDTO, employeeDTO.getEmail(), content);
        interviewService.updateNotificationSent(interviewDTO.getId());

        // Xây dựng thông báo gửi tới User (UserId nhận được mail thông báo)
        NotificationDTO notification = NotificationDTO.builder()
//                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .userID(employeeDTO.getUserID())
                .title("Reminder Notification")
                .link(url)
                .message("You have a interview today, Interview ID: " + interviewDTO.getId())
                .status(false)
                .build();

        // Nếu request là AJAX, trả về JSON; nếu không thì redirect
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Reminder has been sent successfully");
            response.put("notification", notification);
            return ResponseEntity.ok(response);
        } else {
            // Nếu không phải AJAX: chuyển hướng về danh sách interview
            String str = URLEncoder.encode(content, StandardCharsets.UTF_8);
            model.addAttribute("content", str);
            model.addAttribute("title", "Upcoming interview schedule");
            model.addAttribute("employee", employeeDTO.getUserID());
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/interview/list")
                    .build();
        }
    }

    // This below method used to check the upcoming interview schedule for each 5s. Maybe it can cause an unnecessary event, so better comment it.
    // HoanTX
    @Scheduled(fixedDelay = 5000)
    public void notifyDeadline() {
        try {
            List<InterviewDTO> list = checkUpcomingInterview();
            if (!CollectionUtils.isEmpty(list)) {
                log.info("There is {} upcoming interviews at{}", list.size(), LocalTime.now().plusHours(24));
                list.forEach(interviewDTO -> {
                    CandidateDTO candidateDTO = cSrv.getCandidateByUserId(interviewDTO.getCandidateId()).getFirst();
                    String recruiterEmail = empSrv.getEmployeeById(Math.toIntExact(interviewDTO.getRecruiterOwner())).getEmail();
                    String body = writeBodyContent(interviewDTO, candidateDTO, recruiterEmail, INTERVIEW_LINK + "/list");
                    sendEmail(interviewDTO, candidateDTO, recruiterEmail, body);
                    interviewService.updateNotificationSent(interviewDTO.getId());
                });
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // HoanTX
    private List<InterviewDTO> checkUpcomingInterview() {
        return interviewService.getUpcomingInterviewDTOs(
                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    // HoanTX
    private void sendEmail(@NotNull InterviewDTO interviewDTO,
                           @NotNull CandidateDTO candidateDTO,
                           String recruiterEmail,
                           String body) {
        EmailService service = new EmailServiceImpl();
        String content = """
                   <!DOCTYPE html>
                   <html lang="en">
                   <head>
                        <meta charset="UTF-8">
                        <title>Reminder for upcoming interview schedule</title>
                   </head>
                   <body>
                   <p>This email is from IMS system,<p>
                """ + body +
                "Thanks & Regards!<br/>IMS Team</body></html>";
        String subject = "no-reply-email-IMS-system <" + interviewDTO.getTitle() + ">";
        // candidateDTO.getCvLink() not found, so can not send email, better using an existing file in your local computer.
        service.sendEmailAttachFile(recruiterEmail, subject, content, candidateDTO.getCvLink());
        log.info("Email has been sent ");
    }

    // HoanTX
    @NotNull
    private String writeBodyContent(@NotNull InterviewDTO interviewDTO,
                                    @NotNull CandidateDTO candidateDTO,
                                    @NotNull String recruiterEmail,
                                    String interviewScheduleURL) {
        String startTime = interviewDTO.getStartTime().getHour() + ":" + interviewDTO.getStartTime().getMinute();
        String endTime = interviewDTO.getEndTime().getHour() + ":" + interviewDTO.getEndTime().getMinute();
        String timeIndicators = interviewDTO.getStartTime().isBefore(LocalTime.NOON) ? " a.m " : " p.m ";
        String str = startTime + timeIndicators + "to " + endTime + timeIndicators;
        log.info("Start to send email. Upcoming interview schedule: {}", interviewDTO);
        return """
                    <p>You have an interview schedule TODAY at
                """ + str + "</p>" +
                "<p>With Candidate " + candidateDTO.getFullName() +
                ", position " + candidateDTO.getPositionName() +
                ", the CV is attached with this no-reply-email</p>" +
                "<p>If anything wrong, please refer recruiter " + recruiterEmail +
                " or visit our website " + interviewScheduleURL + "</p>" +
                "<p>Please join interview room ID: " + interviewDTO.getMeetId() + "</p>";
    }
    // </editor-fold>
}
