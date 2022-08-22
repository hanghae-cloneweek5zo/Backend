package com.sparta.airbnb_clone.dto.response;

import com.sparta.airbnb_clone.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String description;
    private int star;
    private LocalDateTime createdAt;
//    private Double starAvg;

    public ReviewResponseDto(Review review){
        this.reviewId = review.getReviewId();
        this.description = review.getDescript();
        this.star = review.getStar();
        this.createdAt = review.getCreatedAt();
    }

//    public ReviewResponseDto(Review review, List<Review> reviewList, Long )

}