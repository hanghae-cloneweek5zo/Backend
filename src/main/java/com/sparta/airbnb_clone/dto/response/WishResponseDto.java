package com.sparta.airbnb_clone.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Wish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishResponseDto {
    private Long houseId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    public WishResponseDto(Wish wish,String msg) {
        this.houseId = wish.getResponseHouseId(wish);
        this.msg = msg;
    }
    public WishResponseDto(Wish wish){
        this.houseId = wish.getResponseHouseId(wish);
    }

    public WishResponseDto(House house) {
        this.houseId = house.getHouseId();
    }
}
