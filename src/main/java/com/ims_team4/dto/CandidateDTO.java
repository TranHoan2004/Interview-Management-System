package com.ims_team4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// Duc Long
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CandidateDTO {
    private Long id;
    private long highestEducation;
    private int experience;
    private byte[] cv;
    private Long positionID;
    private Long highestEducationID;
    private Long offerID;
}
