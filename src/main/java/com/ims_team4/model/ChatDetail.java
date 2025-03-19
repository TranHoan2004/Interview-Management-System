package com.ims_team4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chatDetail")
public class ChatDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChatDetailID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ChatID", nullable = false)
    private Chat chat;

    @Column(name = "Message", nullable = false)
    private String message;

    @Column(name = "Timestamp", nullable = false, updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "Sender", nullable = false)
    private String sender;

}
