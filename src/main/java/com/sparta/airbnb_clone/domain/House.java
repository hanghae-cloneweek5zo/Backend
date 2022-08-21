package com.sparta.airbnb_clone.domain;

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
    private Long AccommodationId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String nation;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private String checkIn;

    @Column(nullable = false)
    private String checkOut;

//    private List<HouseImg> HouseImgs;

    @ElementCollection
    private List<String> categories;

    @Column(nullable = false)
    private String descript;

    @Column(nullable = false)
    private int starAvg;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member host;

    @Column(nullable = false)
    private int bedRoomCnt;

    @Column(nullable = false)
    private int bedCnt;

//    private Facility facility;

//    @OneToMany(mappedBy = "Accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Reservation> reservations;
}
