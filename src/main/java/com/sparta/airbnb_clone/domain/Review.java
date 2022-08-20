package com.sparta.airbnb_clone.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int star;

    @Column(nullable = false)
    private double starAvg;


    public Review(String description,int star){
        this.description = description;
        this.star = star;
    }


    //멤버 관계 추가
    //포스트 관계 추가
    //업데이트

}
