package com.sparta.airbnb_clone.domain;

import javax.persistence.*;

@Entity
public class HouseImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long HouseImgId;

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    @Column
    private String imgUrl;
}
