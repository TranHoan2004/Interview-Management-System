package com.ims_team4.service.impl;

import com.ims_team4.dto.JobDTO;
import com.ims_team4.model.*;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.repository.*;
import com.ims_team4.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAllWithDetails().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<JobDTO> getJobsForManager(Long userId, String title, Boolean status, Pageable pageable) {
        Page<Job> jobPage = jobRepository.findJobsForManager(userId, title, status, HrRole.ROLE_MANAGER, pageable);

        return jobPage.map(this::toDTO);
    }

    @Override
    public Optional<Job> getJobDetailForManager(Long userId, Long id) {
        Optional<Job> job = jobRepository.findJobDetailForManager(userId, id);

        if (job.isEmpty()) {
            System.out.println("Không tìm thấy công việc với userId={} và jobId={}" + userId + id);
        } else {
            System.out.println("Tìm thấy công việc: {}" + job.get());
        }
        return job;
    }

    @Override
    @Transactional
    public Job createJob(Long managerId, JobDTO jobDTO) {
        Optional<Users> usersOptional = userRepository.findById(managerId);
        if (usersOptional.isEmpty()) {
            throw new RuntimeException("Manager not found");
        }

        Users manager = usersOptional.get();

        try {
            System.out.println("⏳ Đang tạo Job: " + jobDTO.getTitle());
            Job job = new Job();
            job.setTitle(jobDTO.getTitle());
            job.setStartDate(jobDTO.getStartDate());
            job.setEndDate(jobDTO.getEndDate());
            job.setSalaryFrom(jobDTO.getSalaryFrom());
            job.setSalaryTo(jobDTO.getSalaryTo());
            job.setLocation(jobDTO.getLocation());
            job.setStatus(jobDTO.isStatus());
            job.setDescription(jobDTO.getDescription());


            Employee employee = employeeRepository.findById(manager.getId())
                    .orElseThrow(() -> new RuntimeException("❌ Manager không có thông tin Employee."));


            job.setId(employee.getPosition().getId());

            Set<Long> levelIds = jobDTO.getLevelNames().stream().map(Long::valueOf).collect(Collectors.toSet());
            Set<Long> skillIds = jobDTO.getSkillNames().stream().map(Long::valueOf).collect(Collectors.toSet());
            Set<Long> benefitIds = jobDTO.getBenefitNames().stream().map(Long::valueOf).collect(Collectors.toSet());

            System.out.println("🔍 Level IDs: " + levelIds);
            System.out.println("🔍 Skill IDs: " + skillIds);
            System.out.println("🔍 Benefit IDs: " + benefitIds);

            Set<Level> levels = new HashSet<>(levelRepository.findByIdIn(levelIds));
            Set<Skill> skills = new HashSet<>(skillRepository.findByIdIn(skillIds));
            Set<Benefit> benefits = new HashSet<>(benefitRepository.findByIdIn(benefitIds));

            System.out.println("✅ Levels found: " + levels.size());
            System.out.println("✅ Skills found: " + skills.size());
            System.out.println("✅ Benefits found: " + benefits.size());

            if (levels.isEmpty() || skills.isEmpty() || benefits.isEmpty()) {
                throw new RuntimeException("❌ Không tìm thấy Level, Skill hoặc Benefit trong database");
            }

            job.setLevels(levels);
            job.setSkills(skills);
            job.setBenefits(benefits);

            // Lưu job
            Job savedJob = jobRepository.saveJob(job);

            if (savedJob == null || savedJob.getId() == null) {
                throw new RuntimeException("❌ Job không được lưu thành công.");
            }

            System.out.println("✅ Job đã lưu thành công: ID = " + savedJob.getId());
            return savedJob;

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tạo job: " + e.getMessage());
            throw e;
        }

    }

    //Admin
    @Override
    public Page<JobDTO> filterJobsForAdmin(String title, Boolean status, Pageable pageable) {
        Page<Job> JobPageForAdmin = jobRepository.findJobs(title, status, pageable);
        return JobPageForAdmin.map(this::toDTO);
    }

    @Override
    public Optional<Job> getJobDetailForAdmin(Long jobId) {
        return jobRepository.findJobById(jobId);
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
                .levelNames(job.getLevels().stream().map(Level::getName).collect(Collectors.toList()))
                .skillNames(job.getSkills().stream().map(Skill::getName).collect(Collectors.toList()))
                .build();
    }

    private Job toEntity(JobDTO jobDTO, SkillRepository skillRepository,
                         LevelRepository levelRepository) {
        Job job = new Job();
        job.setId(jobDTO.getId());
        job.setTitle(jobDTO.getTitle());
        job.setStartDate(jobDTO.getStartDate());
        job.setEndDate(jobDTO.getEndDate());
        job.setSalaryFrom(jobDTO.getSalaryFrom());
        job.setSalaryTo(jobDTO.getSalaryTo());
        job.setLocation(jobDTO.getLocation());
        job.setStatus(jobDTO.isStatus());
        job.setDescription(jobDTO.getDescription());

        //Convert list level from name to Entity Level
        if (jobDTO.getLevelNames() != null && !jobDTO.getLevelNames().isEmpty()) {
            Set<Level> levels = new HashSet<>(levelRepository.findByNameIn(new HashSet<>(jobDTO.getLevelNames())));
            job.setLevels(levels);
        } else {
            job.setLevels(Collections.emptySet());
        }

        //Convert list skill from name to Entity Skill
        if (jobDTO.getSkillNames() != null && !jobDTO.getSkillNames().isEmpty()) {
            Set<Skill> skills = new HashSet<>(skillRepository.findByNameIn(new HashSet<>(jobDTO.getSkillNames())));
            job.setSkills(skills);
        } else {
            job.setSkills(Collections.emptySet());
        }

        //Convert list benefit from name to Entity Benefit
        if (jobDTO.getBenefitNames() != null && !jobDTO.getBenefitNames().isEmpty()) {
            Set<Benefit> benefits = new HashSet<>(benefitRepository.findByNameIn(new HashSet<>(jobDTO.getBenefitNames())));
            job.setBenefits(benefits);
        } else {
            job.setBenefits(Collections.emptySet());
        }
        return job;
    }

}
