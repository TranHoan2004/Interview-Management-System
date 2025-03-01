package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "job")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// Duc Long
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "salary_from", nullable = false)
    private Long salaryFrom;

    @Column(name = "salary_to", nullable = false)
    private Long salaryTo;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String location;

    @Column(nullable = false)
    private boolean status;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Job 1-M Interview
    @OneToMany(mappedBy = "job")
    private Set<Interview> interviews;

    // <editor-fold> desc="Many To Many relationship"

    // Job M-M Level
    @ManyToMany
    @JoinTable(
            name = "Job_Level",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private Set<Level> levels;

    @ManyToMany
    private Set<Candidate> candidates;

    // Skill M-M Job
    @ManyToMany
    @JoinTable(
            name = "Job_Skill",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @ManyToMany
    @JoinTable(
            name = "Job_Benefit",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "benefit_id")
    )
    private Set<Benefit> benefits;
    // </editor-fold>
}
