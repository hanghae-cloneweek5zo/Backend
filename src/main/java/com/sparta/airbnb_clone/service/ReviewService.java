package com.sparta.airbnb_clone.service;


import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Review;
import com.sparta.airbnb_clone.dto.request.ReviewRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.dto.response.ReviewResponseDto;
import com.sparta.airbnb_clone.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;


    @Transactional
    public ResponseDto<?> createReview(ReviewRequestDto requestDto, HttpServletRequest httpServlet, Long accId){
        if(requestDto.getDescript() == null){
            return ResponseDto.fail("DESCRIPTION_NULL","리뷰를 입력해주세요");
        }

        Review review = new Review(requestDto.getDescript(),requestDto.getStar());

        reviewRepository.save(review);

        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);

        return ResponseDto.success(reviewResponseDto);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getReviews(){
        ArrayList<ReviewResponseDto> reviewResponseDto = new ArrayList<>();

        for(Review review : reviewRepository.findAllBy()){
            reviewResponseDto.add(new ReviewResponseDto(review));
        }

        return ResponseDto.success(reviewResponseDto);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getReviewsStar(Long accId){
        double reviewStarAvg = this.getRevieStarAvg(accId);
        return ResponseDto.success(reviewStarAvg);
    }

    public Double getRevieStarAvg(Long accId){
        List<Review> reviewList = reviewRepository.findAllByHouseAccommodationIdOrderByCreatedAtDesc(accId);
        double starAvg = 0;
        for(Review review : reviewList){
            starAvg += review.getStar();
        }
        return starAvg / reviewList.size();
    }

}
