package com.sparta.airbnb_clone.controller;

import com.sparta.airbnb_clone.dto.request.WishRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @PostMapping("/auth/houses/{houseId}/wishes")
    public ResponseDto<?> createWish(HttpServletRequest request, @PathVariable Long houseId){
        return wishService.toggleWishByHouse(request,houseId);
    }

    @GetMapping("/auth/houses/wishes")
    public ResponseDto<?> getWishes(HttpServletRequest request){
        return wishService.getWishes(request);

    }


}
