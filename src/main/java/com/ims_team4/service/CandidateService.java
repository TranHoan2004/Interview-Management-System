package com.ims_team4.service;

import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.model.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

// Duc Long
public interface CandidateService {
    List<CandidateDTO> getCandidateByUserId(Long userId);

    Optional<Candidate> findCandidateByUserId(Long userId);

    List<CandidateDTO> getAllCandidate();

    List<CandidateDTO> getAllCandidateNoBan();


    @Transactional
    boolean addCandidate(CandidateDTO candidateDTO, HighestLevel highestLevel, Position position, Employee recruiter, Users creadtedUser);

    List<CandidateDTO> getAllCandidate2();

    CandidateDTO getCandidateDetails(Long userId);

    // <editor-fold desc="Code bởi @HaiDang- getCandidateById2">
    CandidateDTO getCandidateById2(Long userId);


    boolean deleteCandidateByUserId(Long userId);


    @Transactional
    void addSkillsToCandidate(Candidate candidate, Set<Skill> skills);

    @Transactional
    boolean toggleBanStatus(Long userId);


    @Transactional
    boolean updateCandidate(CandidateDTO candidateDTO, HighestLevel highestLevel,
                            Set<Skill> updatedSkills, Users user, Position newPosition, Employee newRecruiter);

    int getTotalCandidates();

    int getOpenCandidates();

    int getWaitingForInterviewCandidates();

    int getPassedInterviewCandidates();


    // </editor-fold>
}
