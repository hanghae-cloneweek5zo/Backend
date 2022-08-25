package com.sparta.airbnb_clone.dto.request;

import com.sparta.airbnb_clone.shared.FacilityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequestDto {
    private int minPrice;
    private int maxPrice;
    private int bedRoomCnt;
    private int bedCnt;
    private List<FacilityType> facilities;
}
