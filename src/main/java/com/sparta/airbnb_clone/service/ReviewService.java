package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.Review;
import com.sparta.airbnb_clone.service.MemberService;
import com.sparta.airbnb_clone.dto.request.ReviewRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.dto.response.ReviewResponseDto;
import com.sparta.airbnb_clone.jwt.TokenProvider;
import com.sparta.airbnb_clone.repository.HouseRepository;
import com.sparta.airbnb_clone.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final HouseRepository houseRepository;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createReview(Long houseId, ReviewRequestDto requestDto,
                                       HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember();
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        House house = isPresentHouse(houseId);
        if (house == null) {
            return ResponseDto.fail("HOUSE_NULL", "해당하는 하우스가 없습니다.");
        }
        if (requestDto.getDescript() == null) {
            return ResponseDto.fail("DESCRIPTION_NULL", "리뷰를 입력해주세요");
        }
        Review review = new Review(requestDto.getDescript(), requestDto.getStar(), house, member);
        reviewRepository.save(review);
        house.updateStarAvg(getReviewStarAvg(house));
        member.updatePoint(memberService.addPoint(house.getHost()));
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return ResponseDto.success(reviewResponseDto);
    }

    @Transactional(readOnly = true)
    public Double getReviewStarAvg(House house) {
        List<Review> reviewList = reviewRepository.findAllByHouseOrderByCreatedAtDesc(house);
        double starAvg = 0.0;
        for (Review review : reviewList) {
            System.out.println(review.getStar());
            starAvg += (double) review.getStar();
        }
        double size =  reviewList.size();
        starAvg =  (double)starAvg / size;
        return (double) Math.round(starAvg * 1000) /1000.0;
    }

    @Transactional(readOnly = true)
    public House isPresentHouse(Long houseId) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        return houseOptional.orElse(null);
    }

    @Transactional
    public Member validateMember() {
        return tokenProvider.getMemberFromAuthentication();
    }
}