package com.ims_team4.service;

import com.ims_team4.dto.CandidateDTO;

import java.util.List;

// Duc Long
public interface CandidateService {
    List<CandidateDTO> getCandidateByUserId(Long userId);

    List<CandidateDTO> getAllCandidate();

    List<CandidateDTO> getAllCandidateNoBan();

    List<CandidateDTO> getAllCandidate2();

    CandidateDTO getCandidateDetails(Long userId);

    // <editor-fold desc="Code bởi @HaiDang- getCandidateById2">
    CandidateDTO getCandidateById2(Long userId);


    boolean deleteCandidateByUserId(Long userId);

    boolean banCandidateByUserId(Long userId);

    // </editor-fold>
}
