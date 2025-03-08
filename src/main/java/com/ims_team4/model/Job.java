package com.ims_team4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(exclude = {"skills", "levels", "benefits", "candidates", "interviews"}) // ✅ Ngăn vòng lặp vô hạn
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "salary_from", nullable = false)
    private Long salaryFrom;

    @Column(name = "salary_to", nullable = false)
    private Long salaryTo;

    @ManyToMany(mappedBy = "jobs")
    @JsonIgnore // ✅ Tránh vòng lặp khi serialize JSON
    private Set<Skill> skills;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String location;

    @ManyToMany
    @JoinTable(
            name = "Job_Benefit",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "benefit_id")
    )
    @JsonIgnore
    private Set<Benefit> benefits;

    @Column(nullable = false)
    private boolean status;

    @ManyToMany
    @JoinTable(
            name = "Job_Level",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private Set<Level> levels;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JsonIgnore
    private Set<Candidate> candidates;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Interview> interviews;
}
