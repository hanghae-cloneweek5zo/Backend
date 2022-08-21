package com.sparta.airbnb_clone.domain;


import com.sparta.airbnb_clone.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Timestamped {

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
    private String descript;

    @Column(nullable = false)
    private int star;

    @JoinColumn(name = "house_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Review(String descript, int star) {
        this.descript = descript;
        this.star = star;
    }

    public Review(String descript, int star, House house, Member member) {
        this.descript = descript;
        this.star = star;
        this.house = house;
        this.member = member;
    }


}
