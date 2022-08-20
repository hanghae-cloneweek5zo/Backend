package com.sparta.airbnb_clone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String description;
    private int star;
    private double starAvg;
}