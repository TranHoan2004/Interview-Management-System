package com.ims_team4.dto;

import com.ims_team4.model.utils.HrRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDTO {
    private Long id;
    private LocalDate dob;
    private Integer gender;
    private String email;
    private String address;
    private byte[] avatar;
    private String fullname;
    private String phone;
    private boolean status;
    private String note;
    private HrRole role;
    private String password;
    private long notificationID;
}