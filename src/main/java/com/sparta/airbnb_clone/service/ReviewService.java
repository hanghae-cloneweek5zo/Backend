package com.sparta.airbnb_clone.service;


import com.sparta.airbnb_clone.domain.Review;
import com.sparta.airbnb_clone.dto.request.ReviewRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;


    @Transactional
    public ResponseDto<?> createReview(ReviewRequestDto requestDto, HttpServletRequest httpServlet, Long commentId){
        if(requestDto.getDescription() == null){
            //description
            return ResponseDto.fail("DESCRIPTION_NULL","리뷰를 입력해주세요");
        }
        Review review = new Review(requestDto.getDescription(),requestDto.getStar());

        return ResponseDto.success(review);
    }
}
