package com.ims_team4.dto;

// import com.test_new_database.model.StatusOffer;
import com.ims_team4.model.utils.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// Duc Long
public class OfferDTO {
    private Long id;
    private Long positionID;
    private String interviewInfo;
    private LocalDate contractPeriodFrom;
    private LocalDate contractPeriodTo;
    private String interviewNotes;
    private OfferStatus status;
    private Long contractTypeID;
    private Long levelID;
    private Long departmentID;
    private String recruiterOwnerName;
    private LocalDate dueDate;
    private Long basicSalary;
    private String note;
    private String approvedMan;
}
