package com.ims_team4.controller;

import com.ims_team4.dto.JobDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.Benefit;
import com.ims_team4.model.Job;
import com.ims_team4.model.Level;
import com.ims_team4.model.Skill;
import com.ims_team4.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private BenefitService benefitService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserService userService;


    //    Role Manager
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

        return "Jobs/jobs-manager";
    }

    @GetMapping("/manager/job-detail/{managerId}/{jobId}")
    public String jobDetail(
            @PathVariable("managerId") Long userId,
            @PathVariable("jobId") Long id,
            Model model) {

        Optional<Job> job = jobService.getJobDetailForManager(userId, id);

        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            return "Jobs/job-detail-manager";
        } else {
            return "error";
        }
    }

    @GetMapping("manager/create-job/{managerId}")
    public String createJobForm(
            @PathVariable("managerId") Long managerId,
            Model model) {
        model.addAttribute("jobDTO", new JobDTO());
        model.addAttribute("skills", skillService.getAllSkill());
        model.addAttribute("benefits", benefitService.getAllBenefit());
        model.addAttribute("levels", levelService.getAllLevels());
        return "Jobs/add-job";
    }

    @PostMapping("manager/create-job/{managerId}")
    public String createJob(@PathVariable("managerId") Long managerId,
                            @ModelAttribute("jobDTO") @Valid JobDTO jobDTO,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        //Check endDate >= startDate
        if (!jobDTO.isEndDateValid()) {
            result.rejectValue("endDate", "error.jobDTO", "End date must be after or equal to start date");
        }

        //Check salaryTo >= salaryFrom
        if (!jobDTO.isSalaryValid()) {
            result.rejectValue("salaryTo", "error.jobDTO", "Salary to must be greater than or equal to salary from");
        }

        if (result.hasErrors()) {
            model.addAttribute("skills", skillService.getAllSkill());
            model.addAttribute("benefits", benefitService.getAllBenefit());
            model.addAttribute("levels", levelService.getAllLevels());
            return "Jobs/jobs-manager";
        }


        Optional<UserDTO> manager = userService.getManagerById(managerId);

        if (manager.isEmpty()) {
            model.addAttribute("errorMessage", "Manager not found.");
            return "redirect:/jobs/manager/list/" + managerId;
        }

        try {
            jobService.createJob(managerId, jobDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Job created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create job: " + e.getMessage());
            return "redirect:/jobs/manager/list/" + managerId;
        }

        return "redirect:/jobs/manager/list/" + managerId;
    }

    @GetMapping("manager/edit-job/{managerId}/{jobId}")
    public String jobManagerEdit(@PathVariable("managerId") Long managerId,
                                 @PathVariable("jobId") Long jobId,
                                 Model model) {

        Optional<Job> jobOptional = jobService.getJobDetailForManager(managerId, jobId);

        if (jobOptional.isPresent()) {
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

            return "Jobs/edit-job-manager";
        } else {
            return "error";
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
            return "redirect:/jobs/manager/list/" + managerId;
        }
    }

    @PostMapping("manager/delete-job/{managerId}/{jobId}")
    public String deletedJobForManager(
            @PathVariable("managerId") Long managerId,
            @PathVariable("jobId") Long jobId
    ) {
        jobService.deleteJobById(jobId);
        return "redirect:/jobs/manager/list/" + managerId;
    }


    //    Role Admin
    @GetMapping("admin/listAllJob")
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

        model.addAttribute("jobPage", jobPage);
        model.addAttribute("title", title);
        model.addAttribute("status", status);

        return "Jobs/admin/jobs-admin";
    }

    @GetMapping("/admin/job-detail/{id}")
    public String jobDetailForAdmin(@PathVariable Long id, Model model) {
        Optional<Job> jobDetail = jobService.getJobDetailForAdmin(id);
        if (jobDetail.isPresent()) {
            model.addAttribute("job", jobDetail.get());
            return "Jobs/admin/jobs-detail-admin";
        } else {
            return "error";
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
