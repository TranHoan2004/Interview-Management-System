package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "benefit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "benefit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Job> jobs;
}
