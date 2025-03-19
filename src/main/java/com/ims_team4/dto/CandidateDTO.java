package com.ims_team4.dto;

import com.ims_team4.model.Interview;
import com.ims_team4.model.Skill;
import com.ims_team4.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;
// Duc Long
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CandidateDTO {
    private long highestEducation;
    private int experience;
    private int positionId;
    private String positionName;
    private byte[] cv;
    private Long offerId;
    //Hai Dang
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String ownerHR;
    private String status;
    private LocalDate dob;
    private String address;
    private Integer gender;
    private String recruiter;
    private String note;
    private Set<String> skills;


    // Getter cho cvLink (trả về link giả định nếu có)
    public String getCvLink() {
        return (cv != null) ? "/cvs/" + userId : null;
    }

}
