package com.sparta.airbnb_clone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "wish")
@NoArgsConstructor
@AllArgsConstructor
public class Wish extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member host;

    public Wish(House house, Member host){
        this.host = host;
        this.house = house;
    }

    public Long getResponseHouseId(Wish wish){
        return wish.getHouse().getHouseId();
    }
}
