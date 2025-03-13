package com.ims_team4.dto;

import com.ims_team4.model.Department;
import com.ims_team4.model.Position;
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
    private long user;
    private String email;
    private String password;
    private String fullname;
    private String phone;
    private boolean status;
    private long positionId;
    private String positionName;
    private long departmentId;
    private String departmentName;
    private Long interviewID;
    private HrRole role;
    private String workingName;
}

