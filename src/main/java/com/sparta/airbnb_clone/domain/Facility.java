package com.sparta.airbnb_clone.domain;

import com.sparta.airbnb_clone.shared.FacilityType;
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
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    @Column
    private FacilityType facilityType;
}
