package com.ims_team4.dto;

import com.ims_team4.model.utils.JobLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
// Duc Long
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JobDTO {
    private long id;
    private String title;
    private String skillRequired;
    private LocalDate startDate;
    private long salaryFrom;
    private long salaryTo;
    private String skill;
    private LocalDate endDate;
    private String location;
    private long benefit;
    private boolean status;
    private JobLevel level;
    private String description;
}
