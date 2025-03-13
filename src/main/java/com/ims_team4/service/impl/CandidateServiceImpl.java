package com.ims_team4.service.impl;

import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.model.Candidate;
import com.ims_team4.model.Skill;
import com.ims_team4.repository.CandidateRepository;
import com.ims_team4.service.CandidateService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
// Duc Long
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    private CandidateDTO convertToDTO(@NotNull Candidate candidate) {
        return CandidateDTO.builder()
                .id(candidate.getId())
//                .skill(candidate.getSkills())
//                .highestEducation(candidate.getHighestLevel().getId())
                .experience(candidate.getExperience())
//                .position(candidate.getPosition())
                .cv(candidate.getCv())
                .build();
    }

    @Override
    public List<CandidateDTO> getCandidateById(Long id) {
        List<Candidate> candidates = candidateRepository.getCandidateById(id);
        return candidates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CandidateDTO> getAllCandidate() {
        return candidateRepository.getAllCandidate().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CandidateDTO> getAllCandidateNoBan() {
        return candidateRepository.getAllCandidateNotBan().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CandidateDTO convertToDTO2(Candidate candidate) {
        System.out.println("🔄 Converting Candidate to CandidateDTO...");

        CandidateDTO dto = CandidateDTO.builder()
                .id(candidate.getId())
                .userId(candidate.getUser() != null ? candidate.getUser().getId() : null)
                .fullName(candidate.getUser() != null ? candidate.getUser().getFullname() : "N/A")
                .email(candidate.getUser() != null ? candidate.getUser().getEmail() : "N/A")
                .phone(candidate.getUser() != null ? candidate.getUser().getPhone() : "N/A")
                .dob(candidate.getUser() != null && candidate.getUser().getDob() != null ? candidate.getUser().getDob() : null)
                .address(candidate.getUser() != null ? candidate.getUser().getAddress() : "N/A")
                .gender(candidate.getUser() != null ? candidate.getUser().getGender() : -1)
                .experience(candidate.getExperience())
                .positionId(candidate.getPosition() != null ? candidate.getPosition().getId().intValue() : 0)
                .positionName(candidate.getPosition() != null ? candidate.getPosition().getName() : "N/A")
                .cv(candidate.getCv() != null ? candidate.getCv() : new byte[0])
                .highestEducation(candidate.getHighestLevel() != null ? candidate.getHighestLevel().getId() : 0)
                .status(candidate.getStatus() != null ? candidate.getStatus().toString() : "Unknown")
                .skills(candidate.getSkills() != null
                        ? candidate.getSkills().stream().map(Skill::getName).collect(Collectors.toSet())
                        : Collections.emptySet())
                .recruiter(candidate.getEmployee() != null && candidate.getEmployee().getUser() != null
                        ? candidate.getEmployee().getUser().getFullname()
                        : "N/A")
                .note(candidate.getUser() != null ? candidate.getUser().getNote() : "N/A")
                .build();

        System.out.println("🎯 CandidateDTO After Mapping: " + dto);
        return dto;
    }












    @Override
    public List<CandidateDTO> getAllCandidate2() {
        List<Candidate> candidates = candidateRepository.getAllCandidate();
        return candidates.stream()
                .map(this::convertToDTO2)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDTO getCandidateDetails(Long id) {
        Optional<Candidate> candidate = candidateRepository.findByUserId(id);
        return candidate.map(this::convertToDTO2).orElse(null);
    }

    @Override
    public CandidateDTO getCandidateById2(Long id) {
        System.out.println("🟢 Request for Candidate Details with ID: " + id);

        Optional<Candidate> candidateOpt = candidateRepository.findByUserId(id);

        if (candidateOpt.isEmpty()) {
            System.out.println("❌ Candidate not found!");
            return new CandidateDTO(); // Trả về DTO trống tránh null
        }

        Candidate candidate = candidateOpt.get();
        System.out.println("✅ Candidate found: " + candidate.getUser().getFullname());

        // 👉 Đảm bảo ánh xạ bằng convertToDTO2()
        CandidateDTO dto = convertToDTO2(candidate);

        System.out.println("📢 Passing CandidateDTO to View: " + dto);
        return dto;
    }







}
