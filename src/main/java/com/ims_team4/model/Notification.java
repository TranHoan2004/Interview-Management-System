package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
// HoanTX
public class Notification {
    @Id
    private long id;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String title;

    // False for not seen yet, True for having seen
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Users user;

    // test ai, do not use
    public Notification(String link, String message, String title) {
        this.link = link;
        this.message = message;
        this.title = title;
    }
}
