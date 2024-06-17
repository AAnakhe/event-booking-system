package com.musalasoft.dev.eventbookingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

    @ManyToMany(mappedBy = "attendees", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Event> events;

}
