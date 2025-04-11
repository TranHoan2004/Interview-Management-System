package com.ims_team4.repository;


import com.ims_team4.model.Job;
import com.ims_team4.model.utils.HrRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


//PhongNN
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.skills LEFT JOIN FETCH j.levels")
    List<Job> findAllWithDetails();
    @EntityGraph(attributePaths = {"levels", "benefits", "skills"})
    @Query("SELECT j FROM Job j " +
            "WHERE j.user.id = :employeeId " +
            "AND (:title IS NULL OR :title = '' OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:status IS NULL OR j.status = :status)")
    Page<Job> findJobsByEmployee(@Param("employeeId") Long employeeId,
                                 @Param("title") String title,
                                 @Param("status") Boolean status,
                                 Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "    j.*, " +
            "    j.user_id AS manager_id, " +
            "    GROUP_CONCAT(DISTINCT l.name SEPARATOR ', ') AS level_names, " +
            "    GROUP_CONCAT(DISTINCT s.name SEPARATOR ', ') AS skill_names " +
            "FROM Job j " +
            "LEFT JOIN Job_Level jl ON jl.job_id = j.id " +
            "LEFT JOIN Levels l ON jl.level_id = l.id " +
            "LEFT JOIN Job_Skill js ON js.job_id = j.id " +
            "LEFT JOIN Skill s ON js.skill_id = s.id " +
            "WHERE j.user_id  = :managerId " +
            "AND j.id = :jobId " +
            "GROUP BY j.id, j.user_id",
            nativeQuery = true)
    Optional<Job> findJobDetailForManager(@Param("managerId") Long managerId, @Param("jobId") Long jobId);


    @Query("SELECT j FROM Job j " +
            "WHERE (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:status IS NULL OR j.status = :status)")
    Page<Job> findJobs(@Param("title") String title, @Param("status") Boolean status, Pageable pageable);

    Job saveJob(Job job);

    Optional<Job> findJobById(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM job_candidates WHERE job_id = :jobId", nativeQuery = true)
    void removeJobCandidateLinks(@Param("jobId") Long jobId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Job j WHERE j.id = :id")
    void deleteJobById(@Param("id") Long id);

    @Query("Select count(j) From Job j")
    int countTotalJobs();

    @Query("Select Count(j) From Job j Where j.status = true")
    int countOpenJobs();

    @Query("Select Count(j) From Job j Where j.status = false")
    int countClosedJobs();

    @Query(value = "SELECT COUNT(*) FROM job j WHERE j.start_date >= CURRENT_DATE - INTERVAL 30 DAY", nativeQuery = true)
    int countTotalJobsLastMonth();

    @Query(value = "SELECT COUNT(*) FROM job j WHERE j.status = true AND j.start_date >= CURRENT_DATE - INTERVAL 30 DAY", nativeQuery = true)
    int countOpenJobsLastMonth();

    @Query(value = "SELECT COUNT(*) FROM job j WHERE j.status = false AND j.start_date >= CURRENT_DATE - INTERVAL 30 DAY", nativeQuery = true)
    int countClosedJobsLastMonth();

}
