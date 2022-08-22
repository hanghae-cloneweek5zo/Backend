package com.sparta.airbnb_clone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseMainResponseDto {
    private Long houseId;
    private String title;
    private int distance;
    private int price;
    private double starAvg;
}
