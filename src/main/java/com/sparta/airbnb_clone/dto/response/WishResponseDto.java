package com.sparta.airbnb_clone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishResponseDto {
    private Long houseId;
    private String msg;
}
