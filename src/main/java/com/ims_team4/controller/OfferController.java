package com.ims_team4.controller;

import com.ims_team4.dto.*;
import com.ims_team4.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
// Duc Long
@Controller
public class OfferController {
    private final OfferService offerService;
    private final CandidateService candidateService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final PositionService positionService;

    public OfferController(OfferService offerService, CandidateService candidateService, UserService userService, DepartmentService departmentService, PositionService positionService) {
        this.offerService = offerService;
        this.candidateService = candidateService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.positionService = positionService;
    }

    @GetMapping("/offer")
    public String index(Model model) {
        List<OfferDTO> listO = offerService.getAllOffer();
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        List<UserDTO> listU = userService.getAllUsers();
        List<DepartmentDTO> listD = departmentService.getAllDepartment();
        model.addAttribute("listO", listO);
        model.addAttribute("listC", listC);
        model.addAttribute("listU", listU);
        model.addAttribute("listD", listD);
        return "/recruiter-features/offer";
    }


    @GetMapping("/search")
    public String search(@RequestParam("text") String text, @RequestParam("dep") String dep, @RequestParam("status") String status, Model model) {
        int depid = Integer.parseInt(dep);
        int statusid = Integer.parseInt(status);
        List<OfferDTO> listO1 = offerService.getAllOfferByNameMailDepStatus(text, depid, statusid);
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        List<UserDTO> listU = userService.getAllUsers();
        List<DepartmentDTO> listD = departmentService.getAllDepartment();
        model.addAttribute("listO1", listO1);
        model.addAttribute("listC", listC);
        model.addAttribute("listU", listU);
        model.addAttribute("listD", listD);
        model.addAttribute("text", text);
        model.addAttribute("dep", depid);
        model.addAttribute("status", statusid);
        return "/recruiter-features/resultSearchOffer";
    }

    @GetMapping("/offerdetail/{id}")
    public String offerDetail(@PathVariable Long id, Model model) {
        OfferDTO offer = offerService.getOfferById(id);
        List<UserDTO> listU = userService.getAllUsers();
        List<PositionDTO> listP = positionService.getAllPosition();
        model.addAttribute("listU", listU);
        model.addAttribute("offer", offer);
        model.addAttribute("listP", listP);
        return "/recruiter-features/offerDetail";
    }
}