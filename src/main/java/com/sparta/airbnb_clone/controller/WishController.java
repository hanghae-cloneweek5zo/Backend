package com.sparta.airbnb_clone.controller;

import com.sparta.airbnb_clone.dto.request.WishRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @PostMapping("/auth/houses/{houseId}/wishes")
    public ResponseDto<?> createWish(@RequestBody Long hostId, @PathVariable Long houseId){
        return wishService.toggleWishByHouse(hostId,houseId);
    }



}
