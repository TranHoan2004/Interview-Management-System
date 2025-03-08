package com.ims_team4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
