package com.ims_team4.model;

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
@Table(name = "notification")
// HoanTX
public class Notification {
    @Id
//    @Column(name = "user_id", nullable = false)
    private long id;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @MapsId
//    private Users user;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String title;

    // False for not seen yet, True for having seen
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean status;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "notification")
//    private Set<NotificationDetails> details;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Users user;
}
