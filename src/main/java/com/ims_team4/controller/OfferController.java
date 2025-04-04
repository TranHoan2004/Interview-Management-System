package com.ims_team4.controller;

import com.ims_team4.controller.utils.UrlIdEncoder;
import com.ims_team4.dto.*;
import com.ims_team4.model.utils.CandidateStatus;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.service.*;
import com.ims_team4.utils.email.EmailService;
import com.ims_team4.utils.email.EmailServiceImpl;
import com.ims_team4.utils.excel.ExportExcelFile;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/offer")
// Duc Long
public class OfferController {
    private final OfferService offerService;
    private final CandidateService candidateService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final StatusOfferService statusOfferService;
    private final PositionService positionService;
    private final EmployeeService employeeService;
    private final InterviewService interviewService;
    private final ContractTypeService contractTypeService;
    private final LevelService levelService;
    private final TemplateEngine templateEngine;
    private final ChatDetailService chatDetailService;
    private final ChatService chatService;
    private final Logger logger = Logger.getLogger(OfferController.class.getName());

    public OfferController(CandidateService candidateService, UserService userService,
            DepartmentService departmentService, StatusOfferService statusOfferService, PositionService positionService,
            EmployeeService employeeService, InterviewService interviewService, ContractTypeService contractTypeService,
            LevelService levelService, OfferService offerService, TemplateEngine templateEngine,
            ChatService chatService, ChatDetailService chatDetailService) {
        this.candidateService = candidateService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.statusOfferService = statusOfferService;
        this.positionService = positionService;
        this.employeeService = employeeService;
        this.interviewService = interviewService;
        this.contractTypeService = contractTypeService;
        this.levelService = levelService;
        this.offerService = offerService;
        this.templateEngine = templateEngine;
        this.chatService = chatService;
        this.chatDetailService = chatDetailService;
    }

    @GetMapping("/offer/{encodedId}")
    public String index(@RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size, @PathVariable("encodedId") String encodedId,
            Model model, HttpSession session) {
        int id = UrlIdEncoder.decodeId(encodedId);
        Page<OfferDTO> offerPage = offerService.findPaginatedByEmployee(page, size, id);
        model.addAttribute("listO", offerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", offerPage.getTotalPages());
        List<ChatDTO> listC1 = chatService.getAllChatOfRecruiter(id);
        List<ChatDetailDTO> listLast = chatDetailService.getLastestChatDetailOfRecruiterAndManager();
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        List<UserDTO> listU = userService.getAllUsers();
        List<DepartmentDTO> listD = departmentService.getAllDepartments();
        List<StatusOfferDTO> listS = statusOfferService.getStatusOffer();
        List<InterviewDTO> listI = interviewService.getAllInterviews();
        List<ChatDetailDTO> listL = chatDetailService.getFirstChatDetailOfRecruiter(id);
        List<EmployeeDTO> listE = employeeService.getAllEmployee();
        int mid = 0;
        ChatDetailDTO cdDTO = new ChatDetailDTO();
        for (ChatDetailDTO cd : listL) {
            int chatId = (int) cd.getChatID();
            ChatDTO cDTO = chatService.getChatById(chatId);
            mid = (int) cDTO.getManagerId();
        }
        session.setAttribute("mid", mid);
        model.addAttribute("listC", listC);
        model.addAttribute("listU", listU);
        model.addAttribute("listD", listD);
        model.addAttribute("listS", listS);
        model.addAttribute("listI", listI);
        model.addAttribute("listC1", listC1);
        model.addAttribute("listLast", listLast);
        model.addAttribute("listE", listE);
        model.addAttribute("rid", id);
        return "/recruiter-features/offer";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size, @RequestParam("rid") String ridStr,
            @RequestParam("text") String text, @RequestParam("dep") String dep, @RequestParam("status") String status,
            Model model) {
        int rid = UrlIdEncoder.decodeId(ridStr);
        int depid = Integer.parseInt(dep);
        int statusid = Integer.parseInt(status);
        Page<OfferDTO> listO1 = offerService.getAllOfferByNameMailDepStatus(text, depid, statusid, rid, page, size);
        model.addAttribute("listO1", listO1.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listO1.getTotalPages());
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        List<UserDTO> listU = userService.getAllUsers();
        List<DepartmentDTO> listD = departmentService.getAllDepartments();
        List<StatusOfferDTO> listS = statusOfferService.getStatusOffer();
        model.addAttribute("listC", listC);
        model.addAttribute("listU", listU);
        model.addAttribute("listD", listD);
        model.addAttribute("listS", listS);
        model.addAttribute("text", text);
        model.addAttribute("dep", depid);
        model.addAttribute("status", statusid);
        model.addAttribute("rid", rid);
        return "/recruiter-features/resultSearchOffer";
    }

    @GetMapping("/offerdetail/{id}")
    public String offerDetail(@RequestParam("rid") String ridHash, @PathVariable("id") String encodedId, Model model) {
        OfferDTO offer = offerService.getOfferById(UrlIdEncoder.decodeId(encodedId));
        int rid = UrlIdEncoder.decodeId(ridHash);
        List<UserDTO> listU = userService.getAllUsers();
        List<PositionDTO> listP = positionService.getAllPosition();
        List<EmployeeDTO> listE = employeeService.getAllEmployee();
        List<InterviewDTO> listI = interviewService.getAllInterviews();
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        model.addAttribute("listU", listU);
        model.addAttribute("offer", offer);
        model.addAttribute("listP", listP);
        model.addAttribute("listE", listE);
        model.addAttribute("listI", listI);
        model.addAttribute("listC", listC);
        model.addAttribute("rid", rid);
        EmployeeDTO e = employeeService.getEmployeeById(rid);
        String roleR = e.getRole().name();
        model.addAttribute("roleR", roleR);
        return "/recruiter-features/offerDetail";
    }

    @GetMapping("/editoffer/{id}")
    public String editOffer(@PathVariable("id") String encodedId, @RequestParam("rid") String ridHash, Model model) {
        int id = UrlIdEncoder.decodeId(encodedId);
        int rid = UrlIdEncoder.decodeId(ridHash);
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        model.addAttribute("listC", listC);
        OfferDTO offer = offerService.getOfferById(id);
        model.addAttribute("offer", offer);
        List<UserDTO> listU = userService.getAllUsers();
        model.addAttribute("listU", listU);
        List<PositionDTO> listP = positionService.getAllPosition();
        model.addAttribute("listP", listP);
        HrRole role = HrRole.valueOf("ROLE_MANAGER");
        List<EmployeeDTO> listE = employeeService.getAllEmployeeByRole(role);
        model.addAttribute("listE", listE);
        List<ContractTypeDTO> listCo = contractTypeService.getAllContractType();
        model.addAttribute("listCo", listCo);
        List<LevelDTO> listL = levelService.getAllLevels();
        model.addAttribute("listL", listL);
        List<DepartmentDTO> listD = departmentService.getAllDepartments();
        model.addAttribute("listD", listD);
        HrRole r = HrRole.valueOf("ROLE_RECRUITER");
        List<EmployeeDTO> listR = employeeService.getAllEmployeeByRole(r);
        model.addAttribute("listR", listR);
        model.addAttribute("rid", rid);
        List<InterviewDTO> listI = interviewService.getAllInterviews();
        model.addAttribute("listI", listI);
        List<EmployeeDTO> listAE = employeeService.getAllEmployee();
        model.addAttribute("listAE", listAE);
        return "/recruiter-features/editOffer";
    }

    @GetMapping("/edit")
    public String editOffer(@RequestParam("candidate") String candidateStr,
            @RequestParam("position") String positionStr,
            @RequestParam("approver") String approverStr,
            @RequestParam("interviewId") String interviewIdStr,
            @RequestParam("from") String fromStr,
            @RequestParam("to") String toStr,
            @RequestParam("interviewNote") String interviewNote,
            @RequestParam("contractType") String contractTypeStr,
            @RequestParam("level") String levelStr,
            @RequestParam("department") String departmentStr,
            @RequestParam("recruiterOwner") String recruiterOwnerStr,
            @RequestParam("duedate") String dueDateStr,
            @RequestParam("salary") String salaryStr,
            @RequestParam("note") String note,
            @RequestParam("offerid") String offeridStr,
            @RequestParam("updateBy") String updateByStr,
            @RequestParam("rid") int rid,
            RedirectAttributes redirectAttributes) {
        try {
            if (offeridStr == null || offeridStr.isEmpty()) {
                throw new IllegalArgumentException("Offer ID cannot be null or empty");
            }
            int interviewId = Integer.parseInt(interviewIdStr);
            int offerid = Integer.parseInt(offeridStr);
            int approverid = Integer.parseInt(approverStr);
            int candidateId = Integer.parseInt(candidateStr);
            int updateBy = Integer.parseInt(updateByStr);
            int salary = Integer.parseInt(salaryStr);
            int level = Integer.parseInt(levelStr);
            int department = Integer.parseInt(departmentStr);
            int recruiterOwner = Integer.parseInt(recruiterOwnerStr);
            int positionId = Integer.parseInt(positionStr);
            int contractTypeId = Integer.parseInt(contractTypeStr);

            LocalDate from = LocalDate.parse(fromStr);
            LocalDate to = LocalDate.parse(toStr);
            LocalDate dueDate = LocalDate.parse(dueDateStr);

            boolean success = offerService.editOffer(offerid, salary, from, to, dueDate,
                    interviewNote, interviewId, note, recruiterOwner, candidateId,
                    contractTypeId, department, approverid, level, positionId, updateBy);

            redirectAttributes.addFlashAttribute("msg",
                    success ? "Change has been successfully updated" : "Failed to update change");
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage(), e);
        }
        String role = employeeService.getEmployeeById(rid).getRole().name();
        if (role.equals(HrRole.ROLE_MANAGER.name())) {
            return "redirect:/offer/managerOffer/" + UrlIdEncoder.encodeId(rid);
        } else if (role.equals(HrRole.ROLE_RECRUITER.name())) {
            return "redirect:/offer/offer/" + UrlIdEncoder.encodeId(rid);
        } else {
            return "redirect:/offer/adminOffer/" + UrlIdEncoder.encodeId(rid);
        }
    }

    @GetMapping("/createoffer/{rid}")
    public String createOffer(@PathVariable("rid") String ridHash, Model model) {
        int rid = UrlIdEncoder.decodeId(ridHash);
        List<CandidateDTO> listC = candidateService.getAllCandidateNoBan();
        model.addAttribute("listC", listC);
        List<UserDTO> listU = userService.getAllUsers();
        model.addAttribute("listU", listU);
        List<PositionDTO> listP = positionService.getAllPosition();
        model.addAttribute("listP", listP);
        HrRole role = HrRole.valueOf("ROLE_MANAGER");
        List<EmployeeDTO> listE = employeeService.getAllEmployeeByRole(role);
        model.addAttribute("listE", listE);
        List<ContractTypeDTO> listCo = contractTypeService.getAllContractType();
        model.addAttribute("listCo", listCo);
        List<LevelDTO> listL = levelService.getAllLevels();
        model.addAttribute("listL", listL);
        List<DepartmentDTO> listD = departmentService.getAllDepartments();
        model.addAttribute("listD", listD);
        HrRole r = HrRole.valueOf("ROLE_RECRUITER");
        List<EmployeeDTO> listR = employeeService.getAllEmployeeByRole(r);
        model.addAttribute("listR", listR);
        List<InterviewDTO> listI = interviewService.getAllInterviews();
        model.addAttribute("listI", listI);
        model.addAttribute("rid", rid);
        HrRole roleAdmin = HrRole.valueOf("ROLE_ADMINISTRATOR");
        List<EmployeeDTO> admin = employeeService.getAllEmployeeByRole(roleAdmin);
        model.addAttribute("admin", admin);
        return "/recruiter-features/createOffer";
    }

    @GetMapping("/create")
    public String createOffer(@RequestParam("rid") String rid, @RequestParam("candidate") String candidateStr,
            @RequestParam("position") String positionStr,
            @RequestParam("approver") String approverStr,
            @RequestParam("interviewId") String interviewIdStr,
            @RequestParam("from") String fromStr,
            @RequestParam("to") String toStr,
            @RequestParam("interviewNote") String interviewNote,
            @RequestParam("contractType") String contractTypeStr,
            @RequestParam("level") String levelStr,
            @RequestParam("department") String departmentStr,
            @RequestParam("recruiterOwner") String recruiterOwnerStr,
            @RequestParam("duedate") String dueDateStr,
            @RequestParam("salary") String salaryStr,
            @RequestParam("note") String note,
            RedirectAttributes redirectAttributes) {
        try {
            int interviewId = Integer.parseInt(interviewIdStr);
            int approverid = Integer.parseInt(approverStr);
            int candidateId = Integer.parseInt(candidateStr);
            int salary = Integer.parseInt(salaryStr);
            int level = Integer.parseInt(levelStr);
            int department = Integer.parseInt(departmentStr);
            int recruiterOwner = Integer.parseInt(recruiterOwnerStr);
            int positionId = Integer.parseInt(positionStr);
            int contractTypeId = Integer.parseInt(contractTypeStr);
            int updateBy = Integer.parseInt(rid);
            LocalDate from = LocalDate.parse(fromStr);
            LocalDate to = LocalDate.parse(toStr);
            LocalDate dueDate = LocalDate.parse(dueDateStr);

            boolean success = offerService.createOffer(salary, from, to, dueDate,
                    interviewNote, interviewId, note, recruiterOwner, candidateId,
                    contractTypeId, department, approverid, level, positionId, updateBy);

            redirectAttributes.addFlashAttribute("msg",
                    success ? "Sucessfully created offer" : "Failed to created offer");
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage(), e);
        }

        return "redirect:/offer/offer/" + UrlIdEncoder.encodeId(Integer.parseInt(rid));
    }

    @GetMapping("/popup")
    public String popup() {
        return "/recruiter-features/popup";
    }

    @GetMapping("/canceloffer/{id}")
    public String canceloffer(@PathVariable("id") String id, @RequestParam("rid") String rid) {
        int oid = Integer.parseInt(id);
        int rid1 = Integer.parseInt(rid);
        CandidateStatus candidateStatus = CandidateStatus.valueOf("CANCELLED_OFFER");
        if (offerService.updateStatusOffer(oid, 7, candidateStatus)) {
            return "redirect:/offer/offerdetail/" + UrlIdEncoder.encodeId(oid) + "?rid=" + UrlIdEncoder.encodeId(rid1);
        }
        return "";
    }

    @GetMapping("/approveoffer/{id}")
    public String approveoffer(@PathVariable String id, @RequestParam("rid") String rid) {
        int oid = Integer.parseInt(id);
        int rid1 = Integer.parseInt(rid);
        CandidateStatus candidateStatus = CandidateStatus.valueOf("APPROVED_OFFER");
        offerService.updateStatusOffer(oid, 2, candidateStatus);
        String role = employeeService.getEmployeeById(Integer.parseInt(rid)).getRole().name();
        return "redirect:/offer/offerdetail/" + UrlIdEncoder.encodeId(oid) + "?rid=" + UrlIdEncoder.encodeId(rid1);
    }

    @GetMapping("/rejectoffer/{id}")
    public String rejectoffer(@PathVariable String id, @RequestParam("rid") String rid) {
        int oid = Integer.parseInt(id);
        int rid1 = Integer.parseInt(rid);
        CandidateStatus candidateStatus = CandidateStatus.valueOf("REJECTED_OFFER");
        offerService.updateStatusOffer(oid, 3, candidateStatus);
        return "redirect:/offer/offerdetail/" + UrlIdEncoder.encodeId(oid) + "?rid=" + UrlIdEncoder.encodeId(rid1);
    }

    @GetMapping("/mark/{id}")
    public String mark(@PathVariable int id, @RequestParam("rid") int rid, @RequestParam("cid") int cid) {
        CandidateStatus candidateStatus = CandidateStatus.valueOf("WAITING_FOR_RESPONSE");
        offerService.updateStatusOffer(id, 4, candidateStatus);

        EmployeeDTO e = employeeService.getEmployeeById(rid);
        if (e == null) {
        }

        UserDTO u = userService.getUserById(cid);

        // Lấy dữ liệu cần thiết để tạo Context
        OfferDTO offer = offerService.getOfferById(id);
        System.out.println();

        List<UserDTO> listU = userService.getAllUsers();
        List<PositionDTO> listP = positionService.getAllPosition();
        List<EmployeeDTO> listE = employeeService.getAllEmployee();
        List<InterviewDTO> listI = interviewService.getAllInterviews();
        List<CandidateDTO> listC = candidateService.getAllCandidate();

        Context context = new Context();
        context.setVariable("listU", listU);
        context.setVariable("offer", offer);
        context.setVariable("listP", listP);
        context.setVariable("listE", listE);
        context.setVariable("listI", listI);
        context.setVariable("listC", listC);
        context.setVariable("cid", id);

        sendEmail(u.getEmail(), context);
        return "redirect:/offer/offerdetail/" + UrlIdEncoder.encodeId(id) + "?rid=" + UrlIdEncoder.encodeId(rid);
    }

    private void sendEmail(String email, Context context) {
        EmailService emailService = new EmailServiceImpl();
        String subject = "[Interview Management System] Response Offer";

        try {
            String body = templateEngine.process("recruiter-features/candidateOffer", context);

            emailService.sendNormalEmail(email, subject, body);
            System.out.println("Email sent successfully to ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/acceptoffer/{id}")
    public String acceptoffer(@PathVariable String id, @RequestParam("rid") String rid) {
        int oid = Integer.parseInt(id);
        int rid1 = Integer.parseInt(rid);
        CandidateStatus candidateStatus = CandidateStatus.valueOf("ACCEPTED_OFFER");
        offerService.updateStatusOffer(oid, 5, candidateStatus);
        return "redirect:/offer/offerdetail/" + UrlIdEncoder.encodeId(oid) + "?rid=" + UrlIdEncoder.encodeId(rid1);
    }

    @GetMapping("/declineoffer/{id}")
    public String declineoffer(@PathVariable String id, @RequestParam("rid") String rid) {
        int oid = Integer.parseInt(id);
        int rid1 = Integer.parseInt(rid);
        CandidateStatus candidateStatus = CandidateStatus.valueOf("DECLINED_OFFER");
        offerService.updateStatusOffer(oid, 6, candidateStatus);
        String role = employeeService.getEmployeeById(Integer.parseInt(rid)).getRole().name();
        return "redirect:/offer/offerdetail/" + UrlIdEncoder.encodeId(oid) + "?rid=" + UrlIdEncoder.encodeId(rid1);
    }

    @GetMapping("/candidateOffer/{id}")
    public String candidateOffer(@PathVariable int id, Model model) {
        OfferDTO offer = offerService.getMaxOfferOfCandidate(id);
        List<UserDTO> listU = userService.getAllUsers();
        List<PositionDTO> listP = positionService.getAllPosition();
        List<EmployeeDTO> listE = employeeService.getAllEmployee();
        List<InterviewDTO> listI = interviewService.getAllInterviews();
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        model.addAttribute("listU", listU);
        model.addAttribute("offer", offer);
        model.addAttribute("listP", listP);
        model.addAttribute("listE", listE);
        model.addAttribute("listI", listI);
        model.addAttribute("listC", listC);
        model.addAttribute("cid", id);
        return "/recruiter-features/candidateOffer";
    }

    @GetMapping("/managerOffer/{encodedId}")
    public String managerOffer(@RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size, @PathVariable("encodedId") String encodedId,
            Model model, HttpSession session) {
        int id = UrlIdEncoder.decodeId(encodedId);
        Page<OfferDTO> offerPage = offerService.findPaginatedByEmployee(page, size, id);
        model.addAttribute("listO", offerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", offerPage.getTotalPages());
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        List<UserDTO> listU = userService.getAllUsers();
        List<DepartmentDTO> listD = departmentService.getAllDepartments();
        List<StatusOfferDTO> listS = statusOfferService.getStatusOffer();
        List<InterviewDTO> listI = interviewService.getAllInterviews();
        List<ChatDetailDTO> listL = chatDetailService.getFirstChatDetailOfManager(id);
        int rid = 0;
        ChatDetailDTO cdDTO = new ChatDetailDTO();
        for (ChatDetailDTO cd : listL) {
            int chatId = (int) cd.getChatID();
            ChatDTO cDTO = chatService.getChatById(chatId);
            rid = (int) cDTO.getRecuiterId();
        }
        session.setAttribute("rid", rid);
        model.addAttribute("listC", listC);
        model.addAttribute("listU", listU);
        model.addAttribute("listD", listD);
        model.addAttribute("listS", listS);
        model.addAttribute("listI", listI);
        model.addAttribute("rid", id);
        if (model.containsAttribute("msg")) {
            model.addAttribute("msg", model.asMap().get("msg"));
        }
        return "/recruiter-features/managerOffer";
    }

    @GetMapping("/adminOffer/{encodedId}")
    public String adminOffer(@RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size, @PathVariable("encodedId") String encodedId,
            Model model) {
        int id = UrlIdEncoder.decodeId(encodedId);
        Page<OfferDTO> offerPage = offerService.findPaginatedByEmployee(page, size, id);
        model.addAttribute("listO", offerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", offerPage.getTotalPages());
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        List<UserDTO> listU = userService.getAllUsers();
        List<DepartmentDTO> listD = departmentService.getAllDepartments();
        List<StatusOfferDTO> listS = statusOfferService.getStatusOffer();
        List<InterviewDTO> listI = interviewService.getAllInterviews();
        model.addAttribute("listC", listC);
        model.addAttribute("listU", listU);
        model.addAttribute("listD", listD);
        model.addAttribute("listS", listS);
        model.addAttribute("listI", listI);
        model.addAttribute("rid", id);
        if (model.containsAttribute("msg")) {
            model.addAttribute("msg", model.asMap().get("msg"));
        }
        return "/recruiter-features/managerOffer";
    }

    @GetMapping("/export")
    public void exportToExcel(@RequestParam("rid") String rid, @RequestParam("from") String fromStr,
            @RequestParam("to") String toStr, HttpServletResponse response) throws IOException {
        // Setup response headers for file download
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=OfferList-" + fromStr + "_" + toStr + ".xlsx");
        // + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) +
        LocalDate from = LocalDate.parse(fromStr);
        LocalDate to = LocalDate.parse(toStr);
        List<OfferDTO> listOffers = offerService.getAllOfferFromToOfEid(from, to, Integer.parseInt(rid));

        new ExportExcelFile<>(listOffers)
                .writeHeaderLine(new String[] { "STT", "Candidate", "Position", "Approver", "Interview Info",
                        "Contract Period From", "Contract Period To", "Interview Note", "Status", "Contract Type",
                        "Level", "Department", "Recruiter Owner", "Due date", "Salary", "Note", "Create At" })
                .writeDataLines(new String[] { "id", "candidateName", "positionName", "employeeName", "interviewTitle",
                        "contractPeriodFrom", "contractPeriodTo", "interviewNotes", "statusOfferName",
                        "contractTypeName", "levelName", "departmentName", "recruiterName", "dueDate", "basicSalary",
                        "note", "createAt" }, OfferDTO.class)
                .export(response);
    }
    // , "Candidate", "Position", "Approver", "Interview Info", "Contract Period
    // From", "Contract Period To", "Interview Note", "Status", "Contract Type",
    // "Level", "Department", "Recruiter Owner", "Due date", "Salary", "Note",
    // "Create At"
    // "candidate", "position", "employee", "interview", "contractPeriodFrom",
    // "contractPeriodTo", "interviewNotes", "statusOffer", "contractType", "level",
    // "department", "recruiterOwner", "dueDate", "basicSalary", "note", "createdAt"
}
// </editor-fold>