package com.ims_team4.controller;

import com.ims_team4.dto.JobDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.Job;
import com.ims_team4.model.Users;
import com.ims_team4.service.*;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
                            @ModelAttribute("jobDTO") JobDTO jobDTO,
                            BindingResult result,
                            Model model) {

        System.out.println("Received managerId: " + managerId);
        System.out.println("Received jobDTO: " + jobDTO);

        if (result.hasErrors()) {
            System.out.println("Form has errors: " + result.getAllErrors());
            model.addAttribute("skills", skillService.getAllSkill());
            model.addAttribute("benefits", benefitService.getAllBenefit());
            model.addAttribute("levels", levelService.getAllLevels());
            return "Jobs/jobs-manager";
        }

        // Kiểm tra manager có tồn tại không
        Optional<UserDTO> manager = userService.getManagerById(managerId); // Lấy từ Users thay vì UserDTO

        if (manager.isEmpty()) {
            System.out.println("Error: Manager with ID " + managerId + " not found in database.");
            model.addAttribute("error", "Manager not found.");
            return "Jobs/jobs-manager";
        }

        try {
            System.out.println("Manager found: " + manager.get().getFullname()); // Kiểm tra thông tin manager
            System.out.println("Calling jobService.createJob()");

            jobDTO.setId(null);
            jobService.createJob(managerId, jobDTO);
            System.out.println("Job created successfully!");
        } catch (Exception e) {
            System.out.println("Error while creating job: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "Jobs/jobs-manager";
        }

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
