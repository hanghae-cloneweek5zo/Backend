package com.sparta.airbnb_clone.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
