package com.ims_team4.controller;

import com.ims_team4.config.Constants;
import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.model.Candidate;
import com.ims_team4.model.Employee;
import com.ims_team4.model.Interview;
import com.ims_team4.model.Job;
import com.ims_team4.model.utils.InterviewStatus;
import com.ims_team4.repository.CandidateRepository;
import com.ims_team4.repository.EmployeeRepository;
import com.ims_team4.repository.JobRepository;
import com.ims_team4.service.CandidateService;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.InterviewService;
import com.ims_team4.utils.email.EmailService;
import com.ims_team4.utils.email.EmailServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * InterviewController handles requests related to Interview functionality: - UC16 list schedules - UC18 view details -
 * UC17 create schedule - UC20 edit schedule - UC21 cancel schedule - UC19 submit result - UC22 send reminder
 */
@Controller
@RequestMapping("/interview")
@EnableScheduling
public class InterviewController implements Constants.Link {
    private final InterviewService interviewService;
    private final EmployeeService empSrv;
    private final CandidateService cSrv;
    private final CandidateRepository candidateRepository;
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;
    private final Logger logger = Logger.getLogger(InterviewController.class.getName());

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
        model.addAttribute("interviewPage", interviewPage);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
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
        return "Interview/detail";
    }

    // <editor-fold> desc="UC17: Create new interview schedule"
    @GetMapping("/createInterviewView")
    public String showCreateInterviewForm(Model model) {
        model.addAttribute("interviewForm", new InterviewDTO());
        return "Interview/create";
    }

    @PostMapping("/createInterview")
    public String createInterview(@ModelAttribute("interviewForm") InterviewDTO interviewDTO) {
        // 1) Fetch the needed Entities from their repositories
        Candidate candidate = candidateRepository.findById(interviewDTO.getCandidateId())
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
        model.addAttribute("interview", interviewDTO);
        return "Interview/edit";
    }

    // PUT/POST /interview/editInterview -> Actually update
    @PostMapping("/editInterview")
    public String editInterview(@ModelAttribute("interview") InterviewDTO interviewDTO) {
        // 1) Load the existing Interview entity from DB
        Interview existing = interviewService.findEntityById(interviewDTO.getId());

        // 2) If the user changed candidate or job or employees, re-fetch them:
        if (interviewDTO.getCandidateId() != 0
            && (existing.getCandidate() == null
                || !existing.getCandidate().getId().equals(interviewDTO.getCandidateId()))) {
            Candidate candidate = candidateRepository.findById(interviewDTO.getCandidateId())
                    .orElseThrow(() -> new RuntimeException("Candidate not found!"));
            existing.setCandidate(candidate);
        }

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

    // <editor-fold> desc="UC22: Send a reminder for upcoming schedule (HTML approach). For a quick approach, we might just do a GET or POST link.”

    /**
     * <p>Send API status to detail screen value of true for send a reminder successfully.</p>
     * <p>Look at index.html and /index in HomeController to see how to catch data throw from this below method.</p>
     */
    @GetMapping("/reminder")
    public String sendReminder(@RequestParam Long interviewId, Model model) {
        // use to test
//        public String sendReminder(Model model) {
        logger.info("send reminder");
        String url = INTERVIEW_LINK + "/list";

        InterviewDTO interviewDTO = interviewService.getInterviewById(interviewId);
        CandidateDTO candidateDTO = cSrv.getCandidateByUserId(interviewDTO.getCandidateId()).getFirst();
        EmployeeDTO employeeDTO = empSrv.getEmployeeById(Math.toIntExact(interviewDTO.getRecruiterOwner()));
        String content = writeBodyContent(interviewDTO, candidateDTO, employeeDTO.getEmail(), url);

        sendEmail(interviewDTO, candidateDTO, employeeDTO.getEmail(), content);
        interviewService.updateNotificationSent(interviewDTO.getId());

        String str = URLEncoder.encode(content, StandardCharsets.UTF_8);
        model.addAttribute("content", str);
        model.addAttribute("title", "Upcoming interview schedule");
        model.addAttribute("employee", employeeDTO.getUserID());

        // Use to test
//        String str = URLEncoder.encode("Nắ", StandardCharsets.UTF_8);
//        logger.info(str);
//        model.addAttribute("content", str);
//        model.addAttribute("title", "Upcoming interview schedule");
//        model.addAttribute("employee", 11);
//        logger.info("send data");
        return "index";
    }

    // This below method used to check the upcoming interview schedule for each 5s. Maybe it can cause an unnecessary event, so better comment it.
    @Scheduled(fixedDelay = 5000)
    public void notifyDeadline() {
        try {
            List<InterviewDTO> list = checkUpcomingInterview();
            if (!CollectionUtils.isEmpty(list)) {
//                logger.info("There is " + list.size() + " upcoming interviews at" + LocalTime.now().plusHours(24));
                list.forEach(interviewDTO -> {
                    CandidateDTO candidateDTO = cSrv.getCandidateByUserId(interviewDTO.getCandidateId()).getFirst();
                    String recruiterEmail = empSrv.getEmployeeById(Math.toIntExact(interviewDTO.getId())).getEmail();
                    String body = writeBodyContent(interviewDTO, candidateDTO, recruiterEmail, INTERVIEW_LINK + "/list");
                    sendEmail(interviewDTO, candidateDTO, recruiterEmail, body);
                    interviewService.updateNotificationSent(interviewDTO.getId());
                });
            } else {
//                logger.info("There is no upcoming interviews at" + LocalTime.now().plusHours(24));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private List<InterviewDTO> checkUpcomingInterview() {
        return interviewService.getUpcomingInterviewDTOs(
                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.now());
    }

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
        logger.info(subject);
        // candidateDTO.getCvLink() not found, so can not send email, better using an existing file in your local computer.
        service.sendEmailAttachFile(recruiterEmail, subject, content, candidateDTO.getCvLink());
//        Example:
//        service.sendEmailAttachFile("tranxuanhoan04@gmail.com", subject, content,
//                "C:\\Users\\ADMIN\\OneDrive\\Máy tính\\SWP.txt");
        logger.info("Email has been sent ");
    }

    @NotNull
    private String writeBodyContent(@NotNull InterviewDTO interviewDTO,
                                    @NotNull CandidateDTO candidateDTO,
                                    @NotNull String recruiterEmail,
                                    String interviewScheduleURL) {
        String startTime = interviewDTO.getStartTime().getHour() + ":" + interviewDTO.getStartTime().getMinute();
        String endTime = interviewDTO.getEndTime().getHour() + ":" + interviewDTO.getEndTime().getMinute();
        String timeIndicators = interviewDTO.getStartTime().isBefore(LocalTime.NOON) ? " a.m " : " p.m ";
        String str = startTime + timeIndicators + "to " + endTime + timeIndicators;
        logger.info("Start to send email. Upcoming interview schedule: " + interviewDTO);
        logger.info("Time of interview: " + str);
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
