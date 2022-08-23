package com.sparta.airbnb_clone.dto.response;

import com.sparta.airbnb_clone.shared.Category;
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
    private Category category;
    private String title;
    private String nation;
    private int price;
    private Double starAvg;
    private String imgUrl;
}