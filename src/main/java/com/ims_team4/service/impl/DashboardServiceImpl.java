package com.ims_team4.service.impl;

import com.ims_team4.dto.DashboardStatsDTO;
import com.ims_team4.repository.JobRepository;
import com.ims_team4.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final JobRepository jobRepository;

    public DashboardServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public DashboardStatsDTO getDashboadStats() {
        int totalJob = jobRepository.countTotalJobs();
        int openJob = jobRepository.countOpenJobs();
        int closeJob = jobRepository.countClosedJobs();
        int totalJobLastMonth = jobRepository.countTotalJobsLastMonth();
        int openJobLastMonth = jobRepository.countOpenJobsLastMonth();
        int closeJobLastMonth = jobRepository.countClosedJobsLastMonth();

        double successRate = (totalJob > 0) ? ((double) closeJob / totalJob) * 100 : 0;
        double successRateLastMonth = (totalJobLastMonth > 0) ? ((double) closeJobLastMonth / totalJobLastMonth) * 100 : 0;

        int totalJobChange = totalJobLastMonth > 0 ? (totalJob - totalJobLastMonth) * 100 / totalJobLastMonth : 0;
        int openJobChange = openJobLastMonth > 0 ? (openJob - openJobLastMonth) * 100 / openJobLastMonth : 0;
        int closeJobChange = closeJobLastMonth > 0 ? (closeJob - closeJobLastMonth) * 100 / closeJobLastMonth : 0;
        int successRateChange = (int) (successRate - successRateLastMonth);

        return new DashboardStatsDTO(totalJob, openJob, closeJob,
                successRate, totalJobChange
                , openJobChange, closeJobChange, successRateChange);
    }

}
