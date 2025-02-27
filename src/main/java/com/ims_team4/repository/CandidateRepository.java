package com.ims_team4.repository;

import com.ims_team4.model.Candidate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    // <editor-fold desc="Code bởi @Duc Long- getCandidateById">
    List<Candidate> getCandidateById(Long id);
    // </editor-fold>

    // <editor-fold desc="Code bởi @Duc Long- getAllCadidate">
    List<Candidate> getAllCandidate();
    // </editor-fold>
}
