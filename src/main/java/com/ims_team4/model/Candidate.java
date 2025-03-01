package com.ims_team4.model;

import com.ims_team4.model.utils.OfferStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Data
@Table(name = "Candidate")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// Duc Long
public class Candidate {
    @Id
    @JoinColumn(name = "user_id")
    private Long id;

    @PositiveOrZero(message = "Experience of years must larger or equal to 0")
    private int experience;

    @Lob
    @Column(nullable = false)
    private byte[] cv;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatus status;

    // Candidate M-1 HighestEducation, Candidate M-1 (Current) Position

    // <editor-fold> desc="Many To One relationship"
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "highest_education_id")
    private HighestEducation education;
    // </editor-fold>

    // Candidate M-M Interview, Candidate M-M Job, Candidate M-M Skill

    // <editor-fold> desc="Many To Many relationship"

    @ManyToMany
    @JoinTable(
            name = "candidate_interview",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "interview_id")
    )
    private Set<Interview> interviews;

    @ManyToMany
    @JoinTable(
            name = "candidate_job",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private Set<Job> jobs;

    @ManyToMany
    @JoinTable(
            name = "candidate_skill",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;
    // </editor-fold>

    // Candidate 1-1 Offer, Candidate 1-1 User

    // <editor-fold> desc="One To One relationship"
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Offer offer;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private User user;
    // </editor-fold>
}
