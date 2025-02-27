package com.ims_team4.controller;

import  com.ims_team4.dto.InterviewDTO;
import com.ims_team4.service.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import  java.util.List;

/**
 * VuTD
 */
@Controller
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    /**
     * Create a new interview
     */
    @PostMapping("/createInterview")
    @ResponseBody
    public InterviewDTO createInterview(@RequestBody InterviewDTO interviewDTO) {
        return interviewService.createInterview(interviewDTO);
    }

    /**
     * Update an interview
     */
    @PutMapping("/updateInterview/{id}")
    @ResponseBody
    public InterviewDTO updateInterview(@PathVariable("id") Long id,
                                        @RequestBody InterviewDTO interviewDTO) {
        return interviewService.updateInterview(id, interviewDTO);
    }

    /**
     * Get a single interview by ID
     */
    @GetMapping("/getInterview/{id}")
    @ResponseBody
    public InterviewDTO getInterviewById(@PathVariable("id") Long id) {
        return interviewService.getInterviewById(id);
    }

    /**
     * Get all interviews
     */
    @GetMapping("/getAllInterviews")
    @ResponseBody
    public List<InterviewDTO> getALlInterviews() {
        return interviewService.getAllInterviews();
    }

    /**
     * Cancel an interview
     */
    @PutMapping("/cancelInterview/{id}")
    @ResponseBody
    public ResponseEntity<Void> cancelInterview(@PathVariable("id") Long id) {
        interviewService.cancelInterview(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Submit result and feedback for an interview
     */
    @PutMapping("/submit/{id}")
    @ResponseBody
    public InterviewDTO submitInterviewResult(@PathVariable("id") Long id,
                                              @RequestParam String result,
                                              @RequestParam String feedback) {
        return interviewService.submitInterviewResult(id, result, feedback);
    }

    /**
     * Send reminder
     */
    @PostMapping("/sendReminders")
    @ResponseBody
    public ResponseEntity<Void> sendReminders() {
        interviewService.sendReminderForUpcoming();
        return ResponseEntity.noContent().build();
    }


}
