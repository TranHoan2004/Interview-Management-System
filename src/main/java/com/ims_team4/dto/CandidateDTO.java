package com.ims_team4.dto;

import com.ims_team4.model.Interview;
import com.ims_team4.model.Skill;
import com.ims_team4.model.Position;
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
    private long highestEducation;
    private int experience;
    private int positionId;
    private String positionName;
    private byte[] cv;
    private Long offerId;
}
