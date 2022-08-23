package com.sparta.airbnb_clone.controller;

import com.sparta.airbnb_clone.dto.request.HouseRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping("/houses")
    public ResponseDto<?> createHouse(@RequestBody HouseRequestDto requestDto) {
        return houseService.createHouse(requestDto);
    }

    @GetMapping("/houses")
    public ResponseDto<?> getAllHouses() {
        return houseService.getAllHouses();
    }

    @GetMapping("/houses/{houseId}")
    public ResponseDto<?> getHouse(@PathVariable Long houseId) {
        return houseService.getHouseByHouseId(houseId);
    }
}
