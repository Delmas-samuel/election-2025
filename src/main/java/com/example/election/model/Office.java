package com.example.election.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "bureaux")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Office {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_id")
    private Commune commune;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;


    @Column(nullable = false)
    private int nombreInscrits;

    @Column(nullable = false)
    private int nombreAbsences;

    @Column(nullable = false)
    private int nombreVotants;

    @Column(nullable = false)
    private int bulletinsNuls;

    @OneToMany(mappedBy = "office")
    @JsonIgnore
    private Set<User> users;

    @OneToMany(mappedBy = "office")
    @JsonIgnore
    private Set<Result> results;
}
