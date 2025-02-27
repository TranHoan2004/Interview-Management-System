package com.ims_team4.controller;

import com.ims_team4.dto.*;
import com.ims_team4.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
// <editor-fold desc="Code bởi @Duc Long- OffderController">
@Controller
public class OfferController {
    @Autowired
    private OfferService offerService;
    private CandidateService candidateService;
    private UserService userService;
    private DepartmentService departmentService;
    private StatusOfferService statusOfferService;

    public OfferController(OfferService offerService, CandidateService candidateService, UserService userService, DepartmentService departmentService, StatusOfferService statusOfferService) {
        this.offerService = offerService;
        this.candidateService = candidateService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.statusOfferService = statusOfferService;
    }

    @GetMapping("/offer")
    public String index(Model model) {
        List<OfferDTO> listO = offerService.getAllOffer();
        List<CandidateDTO> listC = candidateService.getAllCandidate();
        List<UserDTO> listU = userService.getAllUsers();
        List<DepartmentDTO> listD = departmentService.getAllDepartment();
        List<StatusOfferDTO> listS = statusOfferService.getStatusOffer();
        model.addAttribute("listO", listO);
        model.addAttribute("listC", listC);
        model.addAttribute("listU", listU);
        model.addAttribute("listD", listD);
        model.addAttribute("listS", listS);
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
        List<StatusOfferDTO> listS = statusOfferService.getStatusOffer();
        model.addAttribute("listO1", listO1);
        model.addAttribute("listC", listC);
        model.addAttribute("listU", listU);
        model.addAttribute("listD", listD);
        model.addAttribute("listS", listS);
        return "/recruiter-features/resultSearchOffer";
    }
}
// </editor-fold>