package com.sparta.airbnb_clone.controller;

import com.sparta.airbnb_clone.dto.request.FilterRequestDto;
import com.sparta.airbnb_clone.dto.request.HouseRequestDto;
import com.sparta.airbnb_clone.dto.request.PageRequest;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.service.HouseService;
import com.sparta.airbnb_clone.shared.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;
    private final PageRequest pageRequest;

    @PostMapping("/houses")
    public ResponseDto<?> createHouse(@RequestBody HouseRequestDto requestDto) {
        return houseService.createHouse(requestDto);
    }

    @GetMapping({"/houses/categories/{category}"})
    public ResponseDto<?> getAllHousesByCategory(@PathVariable Category category) {
        return houseService.getAllHousesByCategory(category);
    }

    @GetMapping("/houses/categories")
    public ResponseDto<?> getAllHouses() {
        return houseService.getAllHouses();
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
