package com.ims_team4.controller;

import com.ims_team4.dto.DashboardStatsDTO;
import com.ims_team4.dto.JobDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.*;
import com.ims_team4.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/jobs")
// Nam Phong
public class JobController {
    private final JobService jobService;
    private final SkillService skillService;
    private final BenefitService benefitService;
    private final LevelService levelService;
    private final UserService userService;
    private final JobExcelImporterService jobExcelImporterService;
    private final DashboardService dashboardService;

    public JobController(JobService jobService, SkillService skillService, BenefitService benefitService,
                         LevelService levelService, UserService userService,
                         JobExcelImporterService jobExcelImporterService, DashboardService dashboardService1) {
        this.jobService = jobService;
        this.skillService = skillService;
        this.benefitService = benefitService;
        this.levelService = levelService;
        this.userService = userService;
        this.jobExcelImporterService = jobExcelImporterService;
        this.dashboardService = dashboardService1;
    }

    // Role Manager
    @GetMapping("/manager/list/{managerId}")
    public String listManagerJobs(
            @PathVariable("managerId") Long userId, // userId của manager
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "") String status,
            @PageableDefault(size = 5) Pageable pageable,
            Model model) {
        Boolean statusFilter = null;
        if ("true".equalsIgnoreCase(status)) {
            statusFilter = true;
        } else if ("false".equalsIgnoreCase(status)) {
            statusFilter = false;
        }

        Page<JobDTO> jobPage = jobService.getJobsForManager(userId, title, statusFilter, pageable);
        model.addAttribute("jobPage", jobPage);
        model.addAttribute("title", title);
        model.addAttribute("status", status);
        model.addAttribute("userId", userId);
        return "Jobs/jobs-list";
    }

    @GetMapping("/manager/job-detail/{managerId}/{jobId}")
    public String jobDetail(
            @PathVariable("managerId") Long userId,
            @PathVariable("jobId") Long id,
            Model model) {
        Optional<Job> job = jobService.getJobDetailForManager(userId, id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            model.addAttribute("userId", userId);
            return "Jobs/job-detail";
        } else {
            return "component/error";
        }
    }

    // <editor-fold> desc="create job"
    @GetMapping("manager/create-job/{managerId}")
    public String createJobForm(
            @PathVariable("managerId") Long managerId,
            Model model) {
        model.addAttribute("jobDTO", new JobDTO());
        model.addAttribute("skills", skillService.getAllSkill());
        model.addAttribute("benefits", benefitService.getAllBenefit());
        model.addAttribute("levels", levelService.getAllLevels());
        model.addAttribute("role", "manager");
        model.addAttribute("userId", managerId);
        return "Jobs/add-job";
    }

    @PostMapping("manager/create-job/{managerId}")
    public String createJob(@PathVariable("managerId") Long managerId,
                            @ModelAttribute("jobDTO") @Valid JobDTO jobDTO,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        // Check endDate >= startDate
        if (!jobDTO.isEndDateValid()) {
            result.rejectValue("endDate", "error.jobDTO", "End date must be after or equal to start date");
        }
        // Check salaryTo >= salaryFrom
        if (!jobDTO.isSalaryValid()) {
            result.rejectValue("salaryTo", "error.jobDTO", "Salary to must be greater than or equal to salary from");
        }
        if (result.hasErrors()) {
            model.addAttribute("skills", skillService.getAllSkill());
            model.addAttribute("benefits", benefitService.getAllBenefit());
            model.addAttribute("levels", levelService.getAllLevels());
            return "Jobs/jobs-list";
        }

        Optional<UserDTO> manager = userService.getManagerById(managerId);

        try {
            if (manager.isEmpty()) {
                throw new Exception("Manager not found.");
            }
            jobService.createJob(managerId, jobDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Job created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create job: " + e.getMessage());

        }
        return "redirect:/jobs/manager/list/" + managerId;
    }
    // </editor-fold>

    // <editor-fold> desc="edit job"
    @GetMapping("manager/edit-job/{managerId}/{jobId}")
    public String jobManagerEdit(@PathVariable("managerId") Long managerId,
                                 @PathVariable("jobId") Long jobId,
                                 Model model) {
        Optional<Job> jobOptional = jobService.getJobDetailForManager(managerId, jobId);

        if (jobOptional.isPresent()) {
            renderJob(jobOptional, model);
            model.addAttribute("userId", managerId);
            return "Jobs/edit-job";
        } else {
            return "component/error";
        }
    }

    @Transactional
    @PostMapping("manager/edit-job/{managerId}/{jobId}")
    public String editJobForManager(@PathVariable("managerId") Long managerId,
                                    @PathVariable("jobId") Long jobId,
                                    @ModelAttribute JobDTO jobDTO,
                                    RedirectAttributes redirectAttributes) {
        boolean success = jobService.editJob(managerId, jobId, jobDTO);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Job updated successfully!");
            return "redirect:/jobs/manager/list/" + managerId;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Job can not update!");
        }
        return "redirect:/jobs/manager/list/" + managerId;
    }
    // </editor-fold>

    @PostMapping("manager/delete-job/{managerId}/{jobId}")
    public String deletedJobForManager(
            @PathVariable("managerId") Long managerId,
            @PathVariable("jobId") Long jobId) {
        jobService.deleteJobById(jobId);
        return "redirect:/jobs/manager/list/" + managerId;
    }

    @PostMapping("manager/import-job/{managerId}")
    public String importJobs(
            @PathVariable("managerId") Long managerId,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addFlashAttribute("successMessage", "Import Job successfully!");
            jobExcelImporterService.importJobsFromExcel(managerId, file);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to import job: " + e.getMessage());
            log.error(e.getMessage(), e);
        }
        return "redirect:/jobs/manager/list/" + managerId;
    }

    // Role Admin
    @GetMapping("/admin/listAllJob")
    public String listJobsForAdmin(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "") String status,
            @PageableDefault(size = 5) Pageable pageable,
            Model model) {
        Boolean statusFilter = null;
        if ("true".equalsIgnoreCase(status)) {
            statusFilter = true;
        } else if ("false".equalsIgnoreCase(status)) {
            statusFilter = false;
        }

        Page<JobDTO> jobPage = jobService.filterJobsForAdmin(title, statusFilter, pageable);
        DashboardStatsDTO stats = dashboardService.getDashboadStats();

        model.addAttribute("jobPage", jobPage);
        model.addAttribute("title", title);
        model.addAttribute("status", status);
        model.addAttribute("stats", stats);
        //return "Jobs/admin/jobs-admin";
        return "/Jobs/jobs-list";
    }

    @GetMapping("/admin/job-detail/{id}")
    public String jobDetailForAdmin(@PathVariable Long id, Model model) {
        Optional<Job> jobDetail = jobService.getJobDetailForAdmin(id);
        if (jobDetail.isPresent()) {
            model.addAttribute("job", jobDetail.get());
            return "Jobs/job-detail";
        } else {
            return "component/error";
        }
    }

    @GetMapping("admin/edit-job/{jobId}")
    public String jobAdminEdit(
            @PathVariable("jobId") Long jobId,
            Model model) {
        Optional<Job> jobOptional = jobService.getJobDetailForAdmin(jobId);

        if (jobOptional.isPresent()) {
            renderJob(jobOptional, model);
            return "Jobs/edit-job";
        } else {
            return "component/error";
        }
    }

    @Transactional
    @PostMapping("admin/edit-job/{jobId}")
    public String editJobForAdmin(
            @PathVariable("jobId") Long jobId,
            @ModelAttribute JobDTO jobDTO,
            RedirectAttributes redirectAttributes) {
        boolean success = jobService.editJobForAdmin(jobId, jobDTO);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Job updated successfully!");
            return "redirect:/jobs/admin/listAllJob";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Job can not update!");
        }
        return "redirect:/jobs/admin/listAllJob";
    }

    @PostMapping("admin/delete-job/{jobId}")
    public String deletedJobForAdmin(
            @PathVariable("jobId") Long jobId) {
        jobService.deleteJobById(jobId);
        return "redirect:/jobs/admin/listAllJob";
    }

    @PostMapping("admin/import-job")
    public String importJobsForAdmin(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes,
            Principal principal) {
        try {
            Users admin = userService.findByEmail(principal.getName());
            if (admin == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Admin not found!");
                return "redirect:/jobs/admin/listAllJob";
            }

            jobExcelImporterService.importJobsFromExcelForAdmin(admin, file);
            redirectAttributes.addFlashAttribute("successMessage", "Import Job successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to import job: " + e.getMessage());
            log.error(e.getMessage(), e);
        }
        return "redirect:/jobs/admin/listAllJob";
    }

    @GetMapping("admin/create-job")
    public String showAddJobForm(
            Model model) {
        model.addAttribute("jobDTO", new JobDTO());
        model.addAttribute("skills", skillService.getAllSkill());
        model.addAttribute("benefits", benefitService.getAllBenefit());
        model.addAttribute("levels", levelService.getAllLevels());
        model.addAttribute("role", "admin");
        return "Jobs/add-job";
    }

    @PostMapping("admin/add-job")
    public String createJobForAdmin(Principal principal,
                                    @ModelAttribute("jobDTO") @Valid JobDTO jobDTO,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("skills", skillService.getAllSkill());
            model.addAttribute("benefits", benefitService.getAllBenefit());
            model.addAttribute("levels", levelService.getAllLevels());
            return "Jobs/jobs-list";
        }

        Users admin = userService.findByEmail(principal.getName());

        try {
            if (admin == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Admin not found!");
                return "redirect:/jobs/admin/listAllJob";
            }
            jobService.createJob(admin.getId(), jobDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Job created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create job: " + e.getMessage());

        }
        return "redirect:/jobs/admin/listAllJob";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(@NotNull EntityNotFoundException ex, @NotNull Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    //Role Interview
    @GetMapping("interview/listJob")
    public String listJobForInterview(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "") String status,
            @PageableDefault(size = 6) Pageable pageable,
            Model model) {
        Boolean statusFilter = null;
        if ("true".equalsIgnoreCase(status)) {
            statusFilter = true;
        } else if ("false".equalsIgnoreCase(status)) {
            statusFilter = false;
        }

        Page<JobDTO> jobPage = jobService.filterJobsForAdmin(title, statusFilter, pageable);

        model.addAttribute("jobPage", jobPage);
        model.addAttribute("title", title);
        model.addAttribute("status", status);
        return "Jobs/jobs-list";
    }

    @GetMapping("/interview/job-detail/{id}")
    public String jobDetailForInterview(@PathVariable Long id, Model model) {
        Optional<Job> jobDetail = jobService.getJobDetailForAdmin(id);
        if (jobDetail.isPresent()) {
            model.addAttribute("job", jobDetail.get());
            return "Jobs/job-detail";
        } else {
            return "component/error";
        }
    }

    private void renderJob(@NotNull Optional<Job> jobOptional, @NotNull Model model) {
        Job job = jobOptional.get();
        JobDTO jobDTO = JobDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .salaryFrom(job.getSalaryFrom())
                .salaryTo(job.getSalaryTo())
                .location(job.getLocation())
                .status(job.isStatus())
                .description(job.getDescription())
                .levelNames(job.getLevels().stream().map(Level::getName).toList())
                .skillNames(job.getSkills().stream().map(Skill::getName).toList())
                .benefitNames(job.getBenefits().stream().map(Benefit::getName).toList())
                .build();
        // Truyền dữ liệu vào Model
        model.addAttribute("job", jobDTO);
        model.addAttribute("levels", levelService.getAllLevels());
        model.addAttribute("skills", skillService.getAllSkill());
        model.addAttribute("benefits", benefitService.getAllBenefit());
    }
}
