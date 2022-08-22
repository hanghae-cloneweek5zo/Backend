package com.sparta.airbnb_clone.dto.request;

import com.sparta.airbnb_clone.shared.Category;
import com.sparta.airbnb_clone.shared.FacilityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseRequestDto {
    private Long memberId;
    private Category category;
    private String title;
    private String nation;
    private String address;
    private String longitude;
    private String latitude;
    private String descript;
    private int price;
    private String checkIn;
    private String checkOut;
    private int bedRoomCnt;
    private int bedCnt;
    private List<String> imgUrls;
    private List<FacilityType> facilities;
}
