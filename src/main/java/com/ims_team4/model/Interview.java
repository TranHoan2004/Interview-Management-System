package com.ims_team4.model;

import com.ims_team4.model.utils.InterviewStatus;
import com.ims_team4.model.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(columnDefinition = "TEXT")
    private String meetId;

    @Column(name = "schedule_time", nullable = false)
    private LocalDateTime scheduleTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private InterviewStatus status;

    private String locations;

    @Column(length = 100)
    private String result;

    @OneToMany(mappedBy = "interview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Column(nullable = false)
    private long recruiterOwner;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToMany(mappedBy = "interview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;
}
