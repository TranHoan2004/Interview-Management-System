package com.ims_team4.controller;

import com.ims_team4.dto.JobDTO;
import com.ims_team4.service.JobService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    //hien thi danh sach cong viec
    @GetMapping
    public String jobList(Model model) {
        List<JobDTO> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "Jobs/jobs";
    }

    //hien thi chi tiet cong viec
    @GetMapping("/job-detail/{id}")
    public String getJobDetail(@PathVariable Long id, Model model) {
        Optional<JobDTO> jobOptional = jobService.getJobById(id);
        if (jobOptional.isEmpty()) {
            return "redirect:/jobs";
        }
        model.addAttribute("job", jobOptional.get());
        return "Jobs/job-detail";
    }


    //hien thi form tao cong viec
    @GetMapping("/create-job")
    public String showCreateForm(Model model) {
        model.addAttribute("job", new JobDTO());
        return "Jobs/add-job"; //trả về form tạo công việc
    }

    //tao cong viec
    @PostMapping("/create")
    public String createJob(@ModelAttribute @Valid JobDTO jobDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("job", jobDTO);
            return "Jobs/add-job";
        }
        jobService.createdJob(jobDTO);
        return "redirect:/jobs";
    }

    //xoa cong viec
    @PostMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        Optional<JobDTO> job = jobService.getJobById(id);
        if (job.isPresent()) {
            jobService.deleteJob(id);
        }
        return "redirect:/jobs";
    }

}
