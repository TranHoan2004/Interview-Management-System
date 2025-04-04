package com.ims_team4.service;


import com.ims_team4.dto.JobDTO;
import com.ims_team4.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<JobDTO> getAllJobs();

    Page<JobDTO> getJobsForManager(Long userId, String title, Boolean status, Pageable pageable);

    Optional<Job> getJobDetailForManager(Long userId, Long id);

    Job createJob(Long managerId, JobDTO jobDTO);

    void deleteJobById(Long id);

    boolean editJob(Long managerId, Long jobId, JobDTO jobDTO);
    //Admin
    Page<JobDTO> filterJobsForAdmin(String title, Boolean status, Pageable pageable);

    Optional<Job> getJobDetailForAdmin(Long jobId);

}