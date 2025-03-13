package com.ims_team4.service;

import com.ims_team4.dto.CandidateDTO;

import java.util.List;

// Duc Long
public interface CandidateService {
    List<CandidateDTO> getCandidateById(Long id);

    List<CandidateDTO> getAllCandidate();

    List<CandidateDTO> getAllCandidateNoBan();

    List<CandidateDTO> getAllCandidate2();

    CandidateDTO getCandidateDetails(Long id);

    // <editor-fold desc="Code bởi @HaiDang- getCandidateById2">
    CandidateDTO getCandidateById2(Long id);
    // </editor-fold>
}
