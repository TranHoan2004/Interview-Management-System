package com.ims_team4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ims_team4.model.utils.CandidateStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ✅ Chỉ dùng id để tránh vòng lặp
@ToString(exclude = {"user", "skills", "highestLevel", "position", "interviews", "offers", "employee"}) // ✅ Tránh vòng lặp khi log
@Table(name = "Candidate")
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include // ✅ Chỉ dùng ID để hash, tránh vòng lặp vô hạn
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JsonIgnore // ✅ Tránh vòng lặp JSON
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "candidate_skill",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonIgnore // ✅ Tránh vòng lặp JSON
    private Set<Skill> skills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "highest_level_id", nullable = false)
    @JsonIgnore // ✅ Hibernate không serialize quan hệ này
    private HighestLevel highestLevel;

    @Column
    private int experience;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    @JsonIgnore // ✅ Tránh vòng lặp
    private Position position;

    @Lob
    @Column(nullable = false)
    private byte[] cv;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ✅ Ngăn Hibernate tải tất cả các Interview khi lấy Candidate
    private Set<Interview> interviews;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ✅ Ngăn Hibernate tải tất cả Offer khi lấy Candidate
    private Set<Offer> offers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidateStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnore // ✅ Tránh vòng lặp
    private Employee employee;
}
