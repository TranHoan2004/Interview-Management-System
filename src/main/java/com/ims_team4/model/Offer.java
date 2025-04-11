package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    // Khoa ngoai cua Employee (Employee 1-M Offer)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate contractPeriodFrom;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate contractPeriodTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    private String interviewNotes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_offer_id", nullable = false)
    private StatusOffer statusOffer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_type_id", nullable = false)
    private ContractType contractType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private long recruiterOwner;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate dueDate;

    private long basicSalary;

    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Offer(String interviewNotes, String note) {
        this.interviewNotes = interviewNotes;
        this.note = note;
    }

    @Override
    public String toString() {
        return "interviewNote:'" + interviewNotes + "', " +
                "note:'" + note + "'";
    }
}
