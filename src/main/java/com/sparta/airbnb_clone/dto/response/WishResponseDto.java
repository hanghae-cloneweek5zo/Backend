package com.sparta.airbnb_clone.dto.response;

import com.sparta.airbnb_clone.domain.Wish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishResponseDto {
    private Long houseId;
    private String msg;

    public WishResponseDto(Wish wish,String msg) {
        this.houseId = wish.getResponseHouseId(wish);
        this.msg = msg;
    }
    public WishResponseDto(Wish wish){
        this.houseId = wish.getResponseHouseId(wish);
    }

}
