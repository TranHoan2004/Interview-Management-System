package com.ims_team4.dto;

import com.ims_team4.model.utils.HrRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EmployeeDTO {
    private Long id;
    private HrRole role;
    private String department;
    private Long interviewID;
    private Long recruiterID;
}
