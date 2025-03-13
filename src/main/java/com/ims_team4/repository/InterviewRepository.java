package com.ims_team4.repository;

import com.ims_team4.model.Interview;
import com.ims_team4.model.utils.InterviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

//    @Query("""
//        SELECT DISTINCT i
//        FROM Interview i
//            LEFT JOIN i.employees e
//        WHERE
//            (:search IS NULL
//               OR LOWER(i.title) LIKE LOWER(CONCAT('%', :search, '%'))
//               OR LOWER(i.note) LIKE LOWER(CONCAT('%', :search, '%'))
//               OR LOWER(i.result) LIKE LOWER(CONCAT('%', :search, '%'))
//               OR LOWER(i.locations) LIKE LOWER(CONCAT('%', :search, '%')))
//          AND (:status IS NULL OR i.status = :status)
//          AND (:employeeId IS NULL OR e.id = :employeeId)
//    """)
//    Page<Interview> searchInterviews(@Param("search") String search,
//                                     @Param("status") InterviewStatus status,
//                                     @Param("employeeId") Long employeeId,
//                                     Pageable pageable);

    @Query("""
        SELECT i
        FROM Interview i
            LEFT JOIN FETCH i.candidate c
        WHERE i.id = :id
    """)
    Optional<Interview> findInterviewWithCandidate(@Param("id") Long id);
}
