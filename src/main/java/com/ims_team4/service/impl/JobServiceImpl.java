package com.ims_team4.service.impl;

import com.ims_team4.dto.JobDTO;
import com.ims_team4.model.Benefit;
import com.ims_team4.model.Job;
import com.ims_team4.repository.BenefitRepository;
import com.ims_team4.repository.JobRepository;
import com.ims_team4.service.JobService;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final BenefitRepository benefitRepository;

    public JobServiceImpl(JobRepository jobRepository, BenefitRepository benefitRepository) {
        this.jobRepository = jobRepository;
        this.benefitRepository = benefitRepository;
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<JobDTO> getJobById(Long id) {
        return jobRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public void createdJob(JobDTO jobDTO) {
        Job job = convertToEntity(jobDTO);
        jobRepository.save(job);
    }

    @Override
    @Transactional
    public void updateJob(Long id, @NotNull JobDTO jobDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found with id: " + id));

        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setSalaryFrom(jobDTO.getSalaryFrom());
        job.setSalaryTo(jobDTO.getSalaryTo());
        job.setLocation(jobDTO.getLocation());
        job.setStatus(jobDTO.isStatus());
        job.setLevel(jobDTO.getLevel());
        job.setSkill(jobDTO.getSkill());
        job.setSkillRequired(jobDTO.getSkillRequired());
        job.setStartDate(jobDTO.getStartDate());
        job.setEndDate(jobDTO.getEndDate());

        // Cập nhật Benefit từ ID
        if (jobDTO.getBenefit() > 0) {
            Benefit benefit = benefitRepository.findById(jobDTO.getBenefit())
                    .orElseThrow(() -> new EntityNotFoundException("Benefit not found with id: " + jobDTO.getBenefit()));
            job.setBenefit(benefit);
        } else {
            job.setBenefit(null);
        }

        jobRepository.save(job);
    }

    @Override
    public boolean deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            return false;
        }
        jobRepository.deleteById(id);
        return true;
    }

    private JobDTO convertToDTO(@NotNull Job job) {
        return JobDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .skillRequired(job.getSkillRequired())
                .startDate(job.getStartDate())
                .salaryFrom(job.getSalaryFrom())
                .salaryTo(job.getSalaryTo())
                .skill(job.getSkill())
                .endDate(job.getEndDate())
                .location(job.getLocation())
                .benefit(job.getBenefit() != null ? job.getBenefit().getId() : 0) // Chỉ lấy ID
                .status(job.isStatus())
                .level(job.getLevel())
                .description(job.getDescription())
                .build();
    }

    @NotNull
    private Job convertToEntity(@NotNull JobDTO jobDTO) {
        Job job = new Job();
        job.setId(jobDTO.getId());
        job.setTitle(jobDTO.getTitle());
        job.setSkillRequired(jobDTO.getSkillRequired());
        job.setStartDate(jobDTO.getStartDate());
        job.setSalaryFrom(jobDTO.getSalaryFrom());
        job.setSalaryTo(jobDTO.getSalaryTo());
        job.setSkill(jobDTO.getSkill());
        job.setEndDate(jobDTO.getEndDate());
        job.setLocation(jobDTO.getLocation());
        job.setStatus(jobDTO.isStatus());
        job.setLevel(jobDTO.getLevel());
        job.setDescription(jobDTO.getDescription());

        // Gán Benefit từ ID
        if (jobDTO.getBenefit() > 0) {
            Benefit benefit = benefitRepository.findById(jobDTO.getBenefit())
                    .orElseThrow(() -> new EntityNotFoundException("Benefit not found with id: " + jobDTO.getBenefit()));
            job.setBenefit(benefit);
        } else {
            job.setBenefit(null);
        }

        return job;
    }
}
