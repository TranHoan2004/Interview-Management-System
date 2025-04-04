package com.ims_team4.model;

import com.ims_team4.model.utils.InterviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(columnDefinition = "TEXT")
    private String meetId;

    @Column(name = "schedule_time", nullable = false)
    private LocalDateTime scheduleTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(nullable = false)
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private InterviewStatus status;

    private String locations;

    @Column(length = 100)
    private String result;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean notificationSent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false)
    private long recruiterOwner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "interview_employee", joinColumns = @JoinColumn(name = "interview_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @OneToMany(mappedBy = "interview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;
}
