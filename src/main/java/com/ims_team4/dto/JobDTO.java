package com.ims_team4.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Positive(message = "Salary from must be greater than 0")
    private long salaryFrom;

    @Positive(message = "Salary to must be greater than 0")
    private long salaryTo;

    @NotNull(message = "End date í required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "Location is required")
    private String location;

    private boolean status;

    @NotBlank(message = "Description is required")
    private String description;

    @NotEmpty(message  = "At least one level must be selected")
    private List<String> levelNames;

    @NotEmpty(message  = "At least one skill must be selected")
    private List<String> skillNames;

    @NotEmpty(message  = "At least one benefit must be selected")
    private List<String> benefitNames;

    public boolean isEndDateValid() {
        return endDate == null || !endDate.isBefore(startDate);
    }

    public boolean isSalaryValid() {
        return salaryFrom <= salaryTo;
    }

}
