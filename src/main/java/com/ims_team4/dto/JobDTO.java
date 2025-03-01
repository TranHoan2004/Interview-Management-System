package com.ims_team4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
// Duc Long
public class JobDTO {
    private long id;
    private String title;
    private LocalDate startDate;
    private long salaryFrom;
    private long salaryTo;
    private LocalDate endDate;
    private String location;
    private boolean status;
    private String description;
}
