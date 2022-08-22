package com.sparta.airbnb_clone.domain;

import com.sparta.airbnb_clone.shared.Category;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class House extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseId;

    @Column(nullable = false)
    private Category category;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member host;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String nation;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private double starAvg;

    @Column(nullable = false)
    private String descript;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String checkIn;

    @Column(nullable = false)
    private String checkOut;

    @Column(nullable = false)
    private int bedRoomCnt;

    @Column(nullable = false)
    private int bedCnt;


    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HouseImg> imgs;


    public void updateStarAvg(double starAvg) {
        this.starAvg = starAvg;
    }

}
