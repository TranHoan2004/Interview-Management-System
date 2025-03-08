package com.ims_team4.repository;

import com.ims_team4.model.Interview;
import com.ims_team4.model.utils.InterviewStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Tran Dang Vu
 */
@Repository
public interface InterviewRepository extends CrudRepository<Interview, Long>, InterviewRepositoryCustom {

    List<Interview> findByStatus(InterviewStatus status);

    List<Interview> findByScheduleTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Interview> getAllInterview();
}