package com.ims_team4.model;

import com.ims_team4.model.utils.HrRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
// HoanTX
public class Employee {
    @Id
    @Column(name = "employee_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "id")
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private HrRole role;

    @Column(nullable = false, unique = true)
    private String workingName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "interview_id")
    private long interviewID;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Candidate> candidates;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Chat> managedChats; // Danh sách các Chat mà Employee là manager

    @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Chat> recruitedChats; // Danh sách các Chat mà Employee là recruiter

//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Job> jobs;
}
