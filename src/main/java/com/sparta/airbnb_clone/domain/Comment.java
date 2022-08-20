package com.sparta.airbnb_clone.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String descript;

    @Column(nullable = false)
    private int star;


    //멤버 관계 추가
    //포스트 관계 추가
    //업데이트

}
