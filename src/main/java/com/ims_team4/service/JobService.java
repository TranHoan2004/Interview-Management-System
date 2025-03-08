package com.ims_team4.service;


import com.ims_team4.dto.JobDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    List<JobDTO> getAllJobs();

    Page<JobDTO> filterJobs(String title, Boolean status, Pageable pageable);
}