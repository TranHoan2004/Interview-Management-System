package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "highest_level")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HighestLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "highestLevel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Candidate> candidates;
}
