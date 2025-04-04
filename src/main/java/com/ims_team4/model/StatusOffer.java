package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "status_offer")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StatusOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String statusName;

    @OneToMany(mappedBy = "statusOffer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Offer> offers;

    public StatusOffer(String statusName) {
        this.statusName = statusName;
    }
}