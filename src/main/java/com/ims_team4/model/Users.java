package com.ims_team4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ims_team4.config.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"candidate", "employee", "jobs", "notifications"}) // ✅ Tránh vòng lặp khi log
// HoanTX
public class Users implements Constants.Regex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Past
    @Temporal(TemporalType.DATE)
    private LocalDate dob;

    // Gender: 0 for male, 1 for female, 2 for others
    @Column(nullable = false)
    @PositiveOrZero(message = "Must be 0, 1 or 2")
    private Integer gender;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = EMAIL_REGEX, message = "Must follow the pattern <account name>@<domain name>")
    private String email;

    private String address;

    @Lob
    @Column(name = "avatar", columnDefinition = "MEDIUMBLOB")
    private byte[] avatar;

    @Column(nullable = false)
    private String fullname;

    @Column(length = 11, unique = true)
    @Size(min = 10, max = 11)
    @Pattern(regexp = PHONE_REGEX)
    private String phone;

    @Column(nullable = false)
    private boolean status;

    private String note;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore // ✅ Tránh vòng lặp Hibernate
    private Candidate candidate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "user")
    @JsonIgnore
    private Employee employee;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Notification> notifications;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Job> jobs;
}

