package com.sparta.airbnb_clone.controller;

import com.sparta.airbnb_clone.dto.request.FilterRequestDto;
import com.sparta.airbnb_clone.dto.request.HouseRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.service.HouseService;
import com.sparta.airbnb_clone.shared.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping("/houses")
    public ResponseDto<?> createHouse(@RequestBody HouseRequestDto requestDto) {
        return houseService.createHouse(requestDto);
    }

    @GetMapping({"/houses/categories", "/houses/categories/{category}"})
    public ResponseDto<?> getAllHousesByCategory(@PathVariable(required = false) Category category) {
        return category == null ?
            houseService.getAllHouses() :
            houseService.getAllHousesByCategory(category);
    }

    @PostMapping("/houses/filter")
    public ResponseDto<?> getAllHousesByFilter(@RequestBody FilterRequestDto requestDto) {
        return houseService.getHousesByFilter(requestDto);
    }

    @GetMapping("/houses/{houseId}")
    public ResponseDto<?> getHouse(@PathVariable Long houseId) {
        return houseService.getHouseByHouseId(houseId);
    }
}
