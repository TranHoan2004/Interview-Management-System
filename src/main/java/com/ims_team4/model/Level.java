package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "levels")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Offer> offers;

    public Level(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name:'" + name + "'";
    }
}
