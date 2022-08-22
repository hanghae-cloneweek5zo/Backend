package com.sparta.airbnb_clone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseImgRequestDto {
    private Long houseId;
    private String imgUrl;
}
