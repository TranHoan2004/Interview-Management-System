package com.ims_team4.service.impl;

import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.model.Candidate;
import com.ims_team4.repository.CandidateRepository;
import com.ims_team4.service.CandidateService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .skill(candidate.getSkill().getId())
                .highestEducation(candidate.getHighestLevel().getId())
                .experience(candidate.getExperience())
                .position(candidate.getPosition())
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
}
