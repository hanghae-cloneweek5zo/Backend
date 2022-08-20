package com.sparta.airbnb_clone.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @Column(nullable = false)
    private Boolean kitchen;

    @Column(nullable = false)
    private Boolean wifi;

    @Column(nullable = false)
    private Boolean workSpace;

    @Column(nullable = false)
    private Boolean airConditional;

}
