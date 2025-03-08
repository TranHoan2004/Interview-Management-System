package com.ims_team4.repository;


import com.ims_team4.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


//PhongNN
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.skills LEFT JOIN FETCH j.levels")
    List<Job> findAllWithDetails();
    List<Job> findByTitleContaining(String title);
    List<Job> findByStatus(Boolean status);
    List<Job> findByTitleContainingAndStatus(String title, Boolean status);
    @Query("SELECT j FROM Job j " +
            "WHERE (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:status IS NULL OR j.status = :status)")
    Page<Job> findJobs(@Param("title") String title, @Param("status") Boolean status, Pageable pageable);



}
