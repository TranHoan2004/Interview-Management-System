package com.ims_team4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @Column(nullable = false, length = 32)
    @Size(min = 8, max = 32, message = "Password must have length from 8 to 32")
    private String password;

    // <editor-fold> desc="One To Many relationship"

    // Manager decides whether the offer can be approved or not.
    @OneToMany(mappedBy = "approveMan", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Offer> solvingOffers;

    // recruiter owner proposes some offers to manager
    @OneToMany(mappedBy = "recruiterOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Offer> proposingOffers;
    // </editor-fold>

    // <editor-fold> desc="One To One relationship"
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    private User user;
    // </editor-fold>

    // <editor-fold> desc="Many To One relationship"
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "interview_id")
    private Interview interview;
    // </editor-fold>
}
