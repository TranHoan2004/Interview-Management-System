package com.ims_team4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;
// Duc Long
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CandidateDTO {
    private Long id;
    private long skill;
    private long highestEducation;
    private int experience;
    private String position;
    private byte[] cv;
    private Set<Long> jobIds;
    private Set<Long> interviewIds;
    private Long offerId;
}
