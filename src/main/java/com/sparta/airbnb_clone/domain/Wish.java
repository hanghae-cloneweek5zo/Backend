package com.sparta.airbnb_clone.domain;

import javax.persistence.*;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

//    @JoinColumn(name = "house_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private House house;
//
//    @JoinColumn(name = "member_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member host;
}
