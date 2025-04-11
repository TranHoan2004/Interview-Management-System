package com.ims_team4.dto;

import com.ims_team4.model.utils.InterviewStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDTO {
    private Long id;
    private String title;
    private String note;
    private String meetId;
    private LocalDate scheduleTime;
    private LocalTime startTime;
    private LocalTime endTime;
    private InterviewStatus status;
    private String locations;
    private String result;
    private long candidateId;
    private long recruiterOwner;
    private Long jobId;
    private Boolean notificationSent;
    private Set<Long> employeeIds;
    private String candidateName;
    private String jobTitle;
    private String interviewerNames;
}