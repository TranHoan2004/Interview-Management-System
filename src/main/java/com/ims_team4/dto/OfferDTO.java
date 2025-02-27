package com.ims_team4.dto;

import com.ims_team4.model.StatusOffer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

// Duc Long
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
    private Long id;
    private long position;
    private String interviewInfo;
    private LocalDate contractPeriodFrom;
    private LocalDate contractPeriodTo;
    private String interviewNotes;
    private StatusOffer statusOffer;
    private long contractType;
    private long level;
    private long department;
    private String recruiterOwner;
    private LocalDate dueDate;
    private long basicSalary;
    private String note;
    private long candidateId;
    private long employeeId;
}
// </editor-fold>