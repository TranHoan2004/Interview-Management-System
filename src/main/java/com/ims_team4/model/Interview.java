package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;
// <editor-fold desc="Code bởi @Duc Long- Interview">
@Entity
@Table(name = "interview")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "interviewer_id", nullable = false)
    private Long interviewerId;

    @Column(name = "schedule_time", nullable = false)
    private LocalDate scheduleTime;

    @Column
    private boolean status;

    private String locations;

    @Column(length = 100)
    private String result;

    @OneToMany(mappedBy = "interview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    @ManyToMany
    @JoinTable(
            name = "candidate_interview",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Candidate> candidates;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;
}
// </editor-fold>
