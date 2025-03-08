package com.ims_team4.dto;

import com.ims_team4.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Duc Long
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
    private Long id;
    private long position;
    private String positionName;
    private long interviewId;
    private String interviewTitle;
    private LocalDate contractPeriodFrom;
    private LocalDate contractPeriodTo;
    private String interviewNotes;
    private long statusOfferId;
    private String statusOfferName;
    private long contractTypeId;
    private String contractTypeName;
    private long levelId;
    private String levelName;
    private long departmentId;
    private String departmentName;
    private long recruiterOwner;
    private String recruiterName;
    private LocalDate dueDate;
    private long basicSalary;
    private String note;
    private long candidateId;
    private long employeeId;
    private String candidateName;
    private String employeeName;
    private LocalDateTime createAt;
    private long updateBy;

}
// </editor-fold>