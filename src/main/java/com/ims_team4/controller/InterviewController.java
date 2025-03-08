package com.ims_team4.controller;

import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.service.InterviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * VuTD
 */
@Controller
@RequestMapping("/interview")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    /**
     * Get all interviews
     */
    @GetMapping("/list")
    public String listInterviews(Model model) {
        List<InterviewDTO> interviews = interviewService.getAllInterviews();
        model.addAttribute("interviews", interviews);
        return "interview-list";
    }

    /**
     * Create a new interview
     */
    @GetMapping("/createInterview")
    public String createInterview(Model model) {
        model.addAttribute("interview", new InterviewDTO());
        return "interview-create";
    }

    @PostMapping("/createInterview")
    public String createInterview(@ModelAttribute("interview") InterviewDTO interviewDTO) {
        interviewService.createInterview(interviewDTO);
        return "redirect:/interviews";
    }

    /**
     * Update an interview
     */
    @GetMapping("/editInterview/{id}")
    public String editInterview(@PathVariable("id") Long id, Model model) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        model.addAttribute("interview", interview);
        return "interview-edit";
    }

//    @PostMapping("/editInterview/{id}")
//    public String updateInterview(@PathVariable("id") Long id, @ModelAttribute("interview") InterviewDTO interviewDTO) {
//        interviewService.updateInterview(id, interviewDTO);
//        return "redirect:/interviews";
//    }

    /**
     * Get a single interview by ID
     */
    @GetMapping("/interviewDetail/{id}")
    public String detailInterview(@PathVariable("id") Long id, Model model) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        model.addAttribute("interview", interview);
        return "interview-detail";
    }


    /**
     * Cancel an interview
     */
    @GetMapping("/cancelInterview/{id}")
    public String cancelInterview(@PathVariable("id") Long id) {
        interviewService.cancelInterview(id);
        return "redirect:/interviews";
    }

    /**
     * Submit result and feedback for an interview
     */
    @GetMapping("/submit/{id}")
    public String submitInterview(@PathVariable("id") Long id, Model model) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        model.addAttribute("interview", interview);
        return "interview-submit";
    }

//    @PostMapping("/submit/{id}")
//    public String submitInterviewResult(@PathVariable("id") Long id, @RequestParam("result") String result, @RequestParam("feedback") String feedback) {
//        interviewService.submitInterviewResult(id, result, feedback);
//        return "redirect:/interviews";
//    }

    /**
     * Send reminder
     */
//    @GetMapping("/sendReminders")
//    public String sendReminder(){
//        interviewService.sendReminders();
//        return "redirect:/interviews?reminders=sent";
//    }


}
