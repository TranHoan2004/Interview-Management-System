package com.ims_team4.repository;


import com.ims_team4.model.Job;
import com.ims_team4.model.utils.HrRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


//PhongNN
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.skills LEFT JOIN FETCH j.levels")
    List<Job> findAllWithDetails();
    List<Job> findByTitleContaining(String title);
    List<Job> findByStatus(Boolean status);
    List<Job> findByTitleContainingAndStatus(String title, Boolean status);
    @Query("SELECT j FROM Job j " +
            "JOIN Employee e ON e.position.id = j.id " +
            "JOIN Users u ON e.id = u.id " +
            "WHERE u.id = :userId AND e.role = :role " +
            "AND (:title IS NULL OR :title = '' OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:status IS NULL OR j.status = :status)")
    Page<Job> findJobsForManager(@Param("userId") Long userId,
                                 @Param("title") String title,
                                 @Param("status") Boolean status,
                                 @Param("role") HrRole role,
                                 Pageable pageable);

    @Query(value = "SELECT " +
            "    j.*, " +
            "    e.id AS manager_id, " +
            "    GROUP_CONCAT(DISTINCT l.name SEPARATOR ', ') AS level_names, " +
            "    GROUP_CONCAT(DISTINCT s.name SEPARATOR ', ') AS skill_names " +
            "FROM Job j " +
            "JOIN employee e ON e.position_id = j.id " +
            "JOIN users u ON e.id = u.id " +
            "JOIN job_level jl ON jl.job_id = j.id " +
            "JOIN levels l ON jl.level_id = l.id " +
            "JOIN job_skill js ON js.job_id = j.id " +
            "JOIN skill s ON js.skill_id = s.id " +
            "WHERE u.id = :managerId " +
            "AND e.role = 'ROLE_MANAGER' " +
            "AND j.id = :jobId " +
            "GROUP BY j.id, e.id",
            nativeQuery = true)
    Optional<Job> findJobDetailForManager(@Param("managerId") Long managerId, @Param("jobId") Long jobId);

    @Query("SELECT j FROM Job j " +
            "WHERE (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:status IS NULL OR j.status = :status)")
    Page<Job> findJobs(@Param("title") String title, @Param("status") Boolean status, Pageable pageable);

    Job saveJob(Job job);

    Optional<Job> findJobById(Long id);
}
