package com.ims_team4.controller;

import com.ims_team4.dto.JobDTO;
import com.ims_team4.model.Job;
import com.ims_team4.service.JobService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;
    
    @GetMapping("/list")
    public String listJobs(
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

        Page<JobDTO> jobPage = jobService.filterJobs(title, statusFilter, pageable);

        model.addAttribute("jobPage", jobPage);
        model.addAttribute("title", title);
        model.addAttribute("status", status);


        return "Jobs/jobs";
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
