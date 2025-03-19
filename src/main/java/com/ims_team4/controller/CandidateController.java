package com.ims_team4.controller;

import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidate")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    /**
     * UC05: View Candidates List. GET /candidate/list
     */
    @GetMapping("/list")
    public String viewCandidateList(Model model) {
        List<CandidateDTO> candidates = candidateService.getAllCandidate2();
        model.addAttribute("candidates", candidates);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                model.addAttribute("user", principal);
            }
        }

        return "Candidate/candidatelist";
    }


    /**
     * UC07: View candidate details. GET /candidate/details
     */
    // Hiển thị chi tiết ứng viên
    @GetMapping("/details/{id}")
    public String getCandidateDetails(@PathVariable Long id, Model model) {
        System.out.println("🟢 Request for Candidate Details with ID: " + id);
        CandidateDTO candidate = candidateService.getCandidateById2(id);

        if (candidate == null) {
            throw new RuntimeException("Candidate not found");
        }

        System.out.println("📢 Passing CandidateDTO to View: " + candidate);

        model.addAttribute("candidate", candidate);
        return "Candidate/candidate-details";
    }

    @GetMapping("/add")
    public String showCreateCandidateForm(Model model) {
        return "Candidate/create-candidate";
    }


    @GetMapping("/delete/{userId}")
    public String deleteCandidate(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        CandidateDTO candidate = candidateService.getCandidateDetails(userId);

        if (candidate == null) {
            redirectAttributes.addFlashAttribute("error", "Candidate not found.");
            return "redirect:/candidate/list";
        }

        if (!"OPEN".equalsIgnoreCase(candidate.getStatus())) {
            redirectAttributes.addFlashAttribute("error", "Candidate cannot be deleted unless status is 'OPEN'.");
            return "redirect:/candidate/list";
        }

        boolean isDeleted = candidateService.deleteCandidateByUserId(userId);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("success", "Candidate deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error deleting candidate.");
        }

        return "redirect:/candidate/list";
    }

    @PostMapping("/ban/{userId}")
    public String banCandidate(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        boolean isBanned = candidateService.banCandidateByUserId(userId);

        if (isBanned) {
            redirectAttributes.addFlashAttribute("banSuccess", "Candidate banned successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error banning candidate or candidate is already banned.");
        }

        return "redirect:/candidate/list";
    }


}
