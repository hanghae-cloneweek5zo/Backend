package com.sparta.airbnb_clone.dto.response;

import com.sparta.airbnb_clone.domain.*;
import com.sparta.airbnb_clone.shared.Category;
import com.sparta.airbnb_clone.shared.FacilityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseDetailResponseDto {
    private Long houseId;
    private Category category;
    private HostResponseDto host;
    private String title;
    private String nation;
    private String address;
    private String longitude;
    private String latitude;
    private String descript;
    private Double starAvg;
    private int price;
    private String checkIn;
    private String checkOut;
    private int bedRoomCnt;
    private int bedCnt;
    private List<FacilityType> facilities;
    private List<String> houseImgs;
    private List<ReviewResponseDto> reviews;
    private List<Reservation> reservations;
}
