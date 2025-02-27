package com.ims_team4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
// Duc Long
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InterviewDTO {
    private long id;
    private String feedback;
    private long jobId;
    private long interviewerId;
    private LocalDateTime scheduleTime;
    private boolean status;
    private String locations;
    private String result;
}
