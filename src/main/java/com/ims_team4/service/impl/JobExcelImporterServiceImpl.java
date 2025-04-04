package com.ims_team4.service.impl;

import com.ims_team4.model.Benefit;
import com.ims_team4.model.Job;
import com.ims_team4.model.Users;
import com.ims_team4.repository.*;
import com.ims_team4.service.JobDataParserService;
import com.ims_team4.service.JobExcelImporterService;
import com.ims_team4.utils.excel.ImportExcelFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;

@Service
public class JobExcelImporterServiceImpl implements JobExcelImporterService {

    private final UserRepository userRepository;
    private final ImportExcelFile importExcelFile;
    private final JobDataParserService jobDataParserService;
    private final SkillRepository skillRepository;
    private final LevelRepository levelRepository;
    private final BenefitRepository benefitRepository;
    private final JobRepository jobRepository;

    public JobExcelImporterServiceImpl(UserRepository userRepository, ImportExcelFile importExcelFile, JobDataParserService jobDataParserService, SkillRepository skillRepository, LevelRepository levelRepository, BenefitRepository benefitRepository, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.importExcelFile = importExcelFile;
        this.jobDataParserService = jobDataParserService;
        this.skillRepository = skillRepository;
        this.levelRepository = levelRepository;
        this.benefitRepository = benefitRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    @Transactional
    public void importJobsFromExcel(Long managerId, MultipartFile file) {
        try {
            Users users = userRepository.getUserById(managerId);
            if (users == null) {
                throw new RuntimeException("Manager not found");
            }

            List<List<String>> data = importExcelFile.getData(file);
            if (data.size() <= 1) return;

            for (int i = 1; i < data.size(); i++) {
                List<String> row = data.get(i);
                Job job = new Job();

                try {
                    job.setTitle(row.get(0));
                    job.setStartDate(jobDataParserService.parseExcelDate(row.get(1)));
                    job.setSalaryFrom(jobDataParserService.parseSalary(row.get(2)));
                    job.setSalaryTo(jobDataParserService.parseSalary(row.get(3)));
                    job.setEndDate(jobDataParserService.parseExcelDate(row.get(4)));
                    job.setLocation(row.get(5));
                    job.setStatus(Boolean.parseBoolean(row.get(6)));
                    job.setDescription(row.get(7));
                    job.setUser(users);
                    job.setSkills(new HashSet<>(skillRepository.findByNameIn(jobDataParserService.parseList(row.get(8)))));
                    job.setLevels(new HashSet<>(levelRepository.findByNameIn(jobDataParserService.parseList(row.get(9)))));
                    job.setBenefits(new HashSet<>(benefitRepository.findByNameIn(jobDataParserService.parseList(row.get(10)))));
                    jobRepository.saveJob(job);
                } catch (Exception e) {
                    System.err.println("Error processing row " + (i + 1) + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi import Excel: " + e.getMessage());
        }
    }
}
