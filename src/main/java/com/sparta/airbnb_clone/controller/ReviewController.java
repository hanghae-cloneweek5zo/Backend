//package com.sparta.airbnb_clone.controller;
//
//
//import com.sparta.airbnb_clone.dto.request.ReviewRequestDto;
//import com.sparta.airbnb_clone.dto.response.ResponseDto;
//import com.sparta.airbnb_clone.service.ReviewService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@RequiredArgsConstructor
//public class ReviewController {
//    private final ReviewService reviewService;
//
//    @PostMapping("/auth/houses/{houseId}/reviews")
//    public ResponseDto<?> createReview(@RequestBody ReviewRequestDto requestDto, HttpServletRequest request, @PathVariable Long houseId){
//        return reviewService.createReview(requestDto , request, houseId);
//    }
//}
