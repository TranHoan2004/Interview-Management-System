package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;
@Entity
@Table(name = "interview")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// Duc Long
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedback;

    @Column(name = "schedule_time", nullable = false)
    private LocalDate scheduleTime;

    @Column
    private boolean status;

    @Column(nullable = false)
    private String locations;

    @Column(length = 100)
    private String result;

    @OneToMany(mappedBy = "interview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    @ManyToMany
    private Set<Candidate> candidates;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
}
