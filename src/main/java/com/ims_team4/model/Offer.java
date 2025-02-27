package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

// <editor-fold desc="Code bởi @Duc Long- getALlOffer">
@Entity
@Table(name = "offer")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    // Khoa ngoai cua Employee (Employee 1-M Offer)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    private String interviewInfo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate contractPeriodFrom;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate contractPeriodTo;

    private String interviewNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_offer_id", nullable = false)
    private StatusOffer statusOffer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_type_id", nullable = false)
    private ContractType contractType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private String recruiterOwner;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate dueDate;

    private long basicSalary;

    private String note;

    @OneToOne
    @JoinColumn(name = "candidate_id", unique = true, nullable = false)
    private Candidate candidate;
// thieu Employee, doi tuong employee
    // Offer M-1 Employee (Manager)

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

}
// </editor-fold>