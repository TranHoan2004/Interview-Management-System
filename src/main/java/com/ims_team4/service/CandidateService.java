package com.ims_team4.service;

import com.ims_team4.dto.CandidateDTO;

import java.util.List;

// Duc Long
public interface CandidateService {
    List<CandidateDTO> getCandidateById(Long id);

    List<CandidateDTO> getAllCandidate();
}
