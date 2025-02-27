package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;
// <editor-fold desc="Code bởi @Duc Long- Candidate">
@Entity
@Data
@Table(name = "Candidate")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "highest_level_id", nullable = false)
    private HighestLevel highestLevel;

    @Column()
    private int experience;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String position;

    @Lob
    @Column(nullable = false)
    private byte[] cv;

    @ManyToMany
    @JoinTable(
            name = "candidate_job",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Job> jobs;

    @ManyToMany
    private Set<Interview> interviews;

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Offer offer;
}
// </editor-fold>