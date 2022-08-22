package com.sparta.airbnb_clone.dto.request;

import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.shared.CategoryType;
import com.sparta.airbnb_clone.shared.FacilityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseRequestDto {
//    private Long memberId;
    private List<CategoryType> categories;
    private String title;
    private String nation;
    private String address;
    private double longitude;
    private double latitude;
    private String checkIn;
    private String checkOut;
    private int bedRoomCnt;
    private int bedCnt;
    private List<String> imgUrls;
    private String descript;
    private List<FacilityType> facilities;
}
