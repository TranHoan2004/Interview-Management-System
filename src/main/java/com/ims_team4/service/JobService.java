package com.ims_team4.service;

import com.ims_team4.dto.JobDTO;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<JobDTO> getAllJobs();

    Optional<JobDTO> getJobById(Long id);

    void createdJob(JobDTO jobDTO);

    void updateJob(Long id, JobDTO jobDTO);

    boolean deleteJob(Long id);

}
