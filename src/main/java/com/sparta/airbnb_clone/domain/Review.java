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

    @Column(nullable = false)
    private String descript;

    @Column(nullable = false)
    private int star;


    public Review(String descript,int star){
        this.descript = descript;
        this.star = star;
    }


    //멤버 관계 추가
    //포스트 관계 추가
    //업데이트

}
