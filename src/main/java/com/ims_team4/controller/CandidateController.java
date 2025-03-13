package com.ims_team4.controller;

import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER', 'ROLE_MANAGER', 'ROLE_ADMINISTRATOR', 'ROLE_INTERVIEWER')")
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

//    @GetMapping("/details/interviewer/{id}")
//    public String getCandidateDetailsForInterviewer(@PathVariable Long id, Model model) {
//        List<CandidateDTO> candidateDTOs = candidateService.getCandidateById(id);
//        if (!candidateDTOs.isEmpty()) {
//            model.addAttribute("candidate", candidateDTOs.get(0));
//        }
//        return "Candidate/candidate-details-interviewer";
//    }
//
//    @GetMapping("/details/hr/{id}")
//    public String getCandidateDetailsForHR(@PathVariable Long id, Model model) {
//        List<CandidateDTO> candidateDTOs = candidateService.getCandidateById(id);
//        if (!candidateDTOs.isEmpty()) {
//            model.addAttribute("candidate", candidateDTOs.get(0));
//        }
//        return "Candidate/candidate-details";
//    }
//
//    @GetMapping("/details/recruiter/{id}")
//    public String getCandidateDetailsForRecruiter(@PathVariable Long id, Model model) {
//        List<CandidateDTO> candidateDTOs = candidateService.getCandidateById(id);
//        if (!candidateDTOs.isEmpty()) {
//            model.addAttribute("candidate", candidateDTOs.get(0));
//        }
//        return "Candidate/candidate-details";
//    }
//
//    @GetMapping("/details/manager/{id}")
//    public String getCandidateDetailsForManager(@PathVariable Long id, Model model) {
//        List<CandidateDTO> candidateDTOs = candidateService.getCandidateById(id);
//        if (!candidateDTOs.isEmpty()) {
//            model.addAttribute("candidate", candidateDTOs.get(0));
//        }
//        return "Candidate/candidate-details-manager";
//    }


    // Hiển thị chi tiết ứng viên
    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER', 'ROLE_MANAGER', 'ROLE_ADMINISTRATOR', 'ROLE_INTERVIEWER')")
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















}
