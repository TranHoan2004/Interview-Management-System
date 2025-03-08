package com.ims_team4.service.impl;

import com.ims_team4.dto.JobDTO;
import com.ims_team4.model.Job;
import com.ims_team4.model.Level;
import com.ims_team4.model.Skill;
import com.ims_team4.repository.JobRepository;
import com.ims_team4.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;




    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAllWithDetails().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<JobDTO> filterJobs(String title, Boolean status, Pageable pageable) {
        Page<Job> jobPage = jobRepository.findJobs(title, status, pageable);

        // Chuyển đổi từng Job sang JobDTO
        return jobPage.map(this::toDTO);
    }


    private JobDTO toDTO(Job job) {
        return JobDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .salaryFrom(job.getSalaryFrom())
                .salaryTo(job.getSalaryTo())
                .location(job.getLocation())
                .status(job.isStatus())
                .description(job.getDescription())
         //       .leve(job.getLevels().stream().map(Level::getName).collect(Collectors.toList()))
         //       .skillNames(job.getSkills().stream().map(Skill::getName).collect(Collectors.toList()))
                .build();
    }
}
