package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.*;
import com.sparta.airbnb_clone.dto.request.HouseRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.jwt.TokenProvider;
import com.sparta.airbnb_clone.repository.*;
import com.sparta.airbnb_clone.shared.CategoryType;
import com.sparta.airbnb_clone.shared.FacilityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;
    private final CategoryRepository categoryRepository;
    private final FacilityRepository facilityRepository;
    private final HouseImgRepository houseImgRepository;
    private final MemberRepository memberRepository;

    private final TokenProvider tokenProvider;

    // 데이터 수집용 임시 API
    @Transactional
    public ResponseDto<?> createHouse(HouseRequestDto requestDto) {

//        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(
//                () -> new NullPointerException("존재하지 않는 회원")
//        );

//        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
//                () -> new NullPointerException("존재하지 않는 카테고리")
//        );

        House house = House.builder()
//                .host(member)
                .title(requestDto.getTitle())
                .nation(requestDto.getNation())
                .address(requestDto.getAddress())
                .longitude(requestDto.getLongitude())
                .latitude(requestDto.getLatitude())
                .checkIn(requestDto.getCheckIn())
                .checkOut(requestDto.getCheckOut())
                .descript(requestDto.getDescript())
//                .starAvg(requestDto.getStarAvg())
                .bedRoomCnt(requestDto.getBedRoomCnt())
                .bedCnt(requestDto.getBedCnt())
                .build();

        houseRepository.save(house);

        List<Category> categories = new ArrayList<>();

        for (CategoryType type : requestDto.getCategories()) {
            categories.add(
                    Category.builder()
                            .house(house)
                            .type(type)
                            .build()
            );
        }
        categoryRepository.saveAll(categories);

        List<Facility> facilities = new ArrayList<>();

        for (FacilityType type : requestDto.getFacilities()) {
            facilities.add(
                    Facility.builder()
                            .house(house)
                            .type(type)
                            .build()
            );
        }
        facilityRepository.saveAll(facilities);

        List<HouseImg> houseImgs = new ArrayList<>();

        for (String imgUrl : requestDto.getImgUrls()) {
            houseImgs.add(
                    HouseImg.builder()
                            .house(house)
                            .imgUrl(imgUrl)
                            .build()
            );
        }

        houseImgRepository.saveAll(houseImgs);

        return ResponseDto.success("저장 완료");
    }

    @Transactional
    public Member validateMember() {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
