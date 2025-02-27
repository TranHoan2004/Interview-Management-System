package com.ims_team4.repository;

import com.ims_team4.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//PhongNN
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
