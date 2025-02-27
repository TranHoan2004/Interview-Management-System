package com.ims_team4.model;

import com.ims_team4.model.utils.JobLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;
// <editor-fold desc="Code bởi @Duc Long- Job">
@Entity
@Table(name = "job")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "skill_required", nullable = false, columnDefinition = "TEXT")
    private String skillRequired;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "salary_from", nullable = false)
    private Long salaryFrom;

    @Column(name = "salary_to", nullable = false)
    private Long salaryTo;

    @Column(nullable = false)
    private String skill;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefit_id", nullable = false)
    private Benefit benefit;

    @Column(nullable = false)
    private boolean status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobLevel level;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    private Set<Candidate> candidates;

    @OneToOne(mappedBy = "job")
    private Interview interview;

}

// </editor-fold>
