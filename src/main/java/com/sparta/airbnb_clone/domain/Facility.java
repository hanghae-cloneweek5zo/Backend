package com.sparta.airbnb_clone.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    @Column
    private String type;
}
