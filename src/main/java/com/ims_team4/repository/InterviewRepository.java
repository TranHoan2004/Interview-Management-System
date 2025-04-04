package com.ims_team4.repository;

import com.ims_team4.model.Interview;
import com.ims_team4.model.utils.InterviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("""
        SELECT DISTINCT i
        FROM Interview i
          LEFT JOIN i.employees e
          LEFT JOIN e.user eu
          LEFT JOIN i.candidate c
          LEFT JOIN c.user cu
          LEFT JOIN i.job j
        WHERE
            (
              :search IS NULL
              OR LOWER(i.title)         LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(i.note)          LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(i.result)        LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(i.locations)     LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(cu.fullname)     LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(j.title)         LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(eu.fullname)     LIKE LOWER(CONCAT('%', :search, '%'))
            )
          AND (:status IS NULL OR i.status = :status)
          AND (:employeeId IS NULL OR e.id = :employeeId)
    """)
    Page<Interview> searchInterviews(
            @Param("search") String search,
            @Param("status") InterviewStatus status,
            @Param("employeeId") Long employeeId,
            Pageable pageable
    );

    @Query("""
        SELECT i
        FROM Interview i
          LEFT JOIN FETCH i.candidate c
          LEFT JOIN FETCH i.job j
          LEFT JOIN FETCH i.employees e
          LEFT JOIN FETCH i.offers o
        WHERE i.id = :id
    """)
    Optional<Interview> findInterviewDetails(@Param("id") Long id);

    // HoanTX
    @Query("""
            FROM Interview i
            WHERE DATE(i.scheduleTime)=:scheduleTime AND i.startTime<=:startTime AND i.notificationSent=false
            """)
    List<Interview> findInterviewsByStartTimeAndScheduleTime(@Param("scheduleTime") Date schedule,
                                                             @Param("startTime") LocalTime startTime);

    @Modifying
    @Query("""
        UPDATE Interview i
        SET i.status = :newStatus
        WHERE i.id = :id
    """)
    void updateInterviewStatus(@Param("id") Long id,
                               @Param("newStatus") InterviewStatus newStatus);

    // HoanTX
    @Query("""
            UPDATE Interview i SET i.notificationSent=true WHERE i.id=:id
            """)
    @Modifying
    void updateNotificationSent(Long id);

}
