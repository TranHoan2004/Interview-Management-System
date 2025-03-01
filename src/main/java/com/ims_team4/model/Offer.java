package com.ims_team4.model;

import com.ims_team4.model.utils.OfferStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "offer")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// Duc Long
public class Offer {
    // <editor-fold> desc="properties"
    @Id
    @JoinColumn(name = "candidate_id")
    private Long id;

    @Column(name = "interview_info", nullable = false)
    private String interviewInfo;

    @Temporal(TemporalType.DATE)
    @Column(name = "contract_period_from", nullable = false)
    private LocalDate contractPeriodFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "contract_period_to", nullable = false)
    private LocalDate contractPeriodTo;

    @Column(name = "interview_notes")
    private String interviewNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatus status;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(name = "basic_salary", nullable = false)
    private long basicSalary;

    private String note;
    // </editor-fold>

    // <editor-fold> desc="Many To Many relationship"

    // 7. Offer M-1 Position
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    // 8. Offer M-1 ContractType
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_type_id")
    private ContractType contractType;

    // 9. Offer M-1 Level
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;

    // 10. Department 1-M Offer
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // 11. Employee 1-M Offer
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recruiter_owner_id")
    private Employee recruiterOwner;

    // 21. Employee 1-M Offer
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "approve_man_id")
    private Employee approveMan;
    // </editor-fold>

    // 15 Candidate 1-1 Offer
    @OneToOne
    @MapsId
    private Candidate candidate;

}
