package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.Review;
import com.sparta.airbnb_clone.dto.request.ReviewRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.dto.response.ReviewResponseDto;
import com.sparta.airbnb_clone.repository.HouseRepository;
import com.sparta.airbnb_clone.repository.MemberRepository;
import com.sparta.airbnb_clone.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final HouseRepository houseRepository;

    private final MemberRepository memberRepository;


    @Transactional
    public ResponseDto<?> createReview(ReviewRequestDto requestDto, Long hostId, Long houseId) {

        Member member = isPresentMember(hostId);
        House house = isPresentHouse(houseId);
        if (house == null) {
            return ResponseDto.fail("HOUSE_NULL", "해당하는 하우스가 없습니다.");
        }
        if (requestDto.getDescript() == null) {
            return ResponseDto.fail("DESCRIPTION_NULL", "리뷰를 입력해주세요");
        }
        Review review = new Review(requestDto.getDescript(), requestDto.getStar(),house,member);
        house.updateStarAvg(getReviewStarAvg(houseId));
        reviewRepository.save(review);
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return ResponseDto.success(reviewResponseDto);
    }

    @Transactional(readOnly = true)
    public Double getReviewStarAvg(Long houseId) {
        List<Review> reviewList = reviewRepository.findAllByHouseIdOrderByCreatedAtDesc(houseId);
        double starAvg = 0;
        for (Review review : reviewList) {
            starAvg += review.getStar();
        }
        starAvg /= reviewList.size();
        return Math.floor(starAvg * 100) / 100.0;
    }

    @Transactional(readOnly = true)
    public House isPresentHouse(Long houseId) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        return houseOptional.orElse(null);
    }
    @Transactional(readOnly = true)
    public Member isPresentMember(Long memberId){
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        return memberOptional.orElse(null);
    }

}