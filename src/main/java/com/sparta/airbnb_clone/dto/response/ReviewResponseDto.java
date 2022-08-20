package com.sparta.airbnb_clone.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {
    private Long commentId;
    private String description;
    private int star;
}