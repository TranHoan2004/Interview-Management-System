package com.ims_team4.controller;

import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.model.utils.InterviewStatus;
import com.ims_team4.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * InterviewController handles requests related to Interview functionality:
 *   - UC16 list schedules
 *   - UC18 view details
 *   - UC17 create schedule
 *   - UC20 edit schedule
 *   - UC21 cancel schedule
 *   - UC19 submit result
 *   - UC22 send reminder
 */
@Controller
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    /**
     * UC16: View (search) interview schedules.
     * GET /interview/list
     */
    @GetMapping("/list")
    public String getInterviewList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) InterviewStatus status,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Page<InterviewDTO> interviewPage =
                interviewService.searchInterviews(search, status, employeeId, page, size);
        model.addAttribute("interviewPage", interviewPage);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "interview/list";
    }

    /**
     * UC18: View interview schedule details .
     * GET /interview/interviewDetail?id=xxx
     */
    @GetMapping("/interviewDetail")
    public String getInterviewDetail(@RequestParam Long id, Model model) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        model.addAttribute("interview", interview);
        return "Interview/detail";
    }

    /**
     * UC17: Create new interview schedule .
     * GET /interview/createInterviewView -> Show the form.
     * POST /interview/createInterview -> Actually create.
     */

    @GetMapping("/createInterviewView")
    public String showCreateInterviewForm(Model model) {
        model.addAttribute("interviewForm", new InterviewDTO());
        return "Interview/create";
    }

    // 2) Handle form submit
    @PostMapping("/createInterview")
    public String createInterview(@ModelAttribute("interviewForm") InterviewDTO interviewDTO) {
        interviewService.createInterview(interviewDTO);
        // Redirect back to list page (or show a success page)
        return "redirect:/interview/list";
    }

    /**
     * UC20: Edit interview details.
     * GET /interview/editInterviewView?id=xxx -> Show the form
     * PUT/POST /interview/editInterview -> Actually update
     */

    // Show the edit form
    @GetMapping("/editInterviewView")
    public String showEditInterviewForm(@RequestParam Long id, Model model) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        model.addAttribute("interview", interview);
        return "interview/edit";
    }

    @PostMapping("/editInterview")
    public String editInterview(@ModelAttribute("interview") InterviewDTO interviewDTO) {
        interviewService.updateInterview(interviewDTO);
        return "redirect:/interview/list";
    }

    /**
     * UC21: Cancel interview schedule (HTML approach).
     * GET /interview/cancelInterview?id=xxx
     */
    @GetMapping("/cancelInterview")
    public String cancelInterview(@RequestParam Long id) {
        interviewService.cancelInterview(id);
        return "redirect:/interview/list";
    }

    /**
     * UC19: Submit interview result (HTML approach).
     * GET /interview/submitResultView?id=xxx -> Show a form
     * POST /interview/submitResult -> Actually submit
     */

    @GetMapping("/submitResultView")
    public String showSubmitResultForm(@RequestParam Long id, Model model) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        model.addAttribute("interview", interview);
        return "interview/submit-result";
    }

    @PostMapping("/submitResult")
    public String submitInterviewResult(@RequestParam Long interviewId,
                                        @RequestParam String result) {
        interviewService.submitInterviewResult(interviewId, result);
        return "redirect:/interview/list";
    }

    /**
     * UC22: Send reminder for upcoming schedule (HTML approach).
     * For a quick approach, we might just do a GET or POST link.
     */
    @GetMapping("/reminder")
    public String sendReminder(@RequestParam Long interviewId) {
        interviewService.sendReminder(interviewId);
        return "redirect:/interview/list";
    }

}
