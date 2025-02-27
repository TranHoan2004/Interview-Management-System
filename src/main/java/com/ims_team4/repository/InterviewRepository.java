package com.ims_team4.repository;

import com.ims_team4.model.Interview;

import java.util.List;
import java.util.Optional;

/**
 * Tran Dang Vu
 */
public interface InterviewRepository {
    Interview save(Interview interview);

    Optional<Interview> findById(Long id);

    List<Interview> findAll();

    void deleteById(Long id);

    List<Interview> findByInterviewerId(Long interviewId);
}