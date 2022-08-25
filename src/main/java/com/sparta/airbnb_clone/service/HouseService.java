package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.*;
import com.sparta.airbnb_clone.dto.request.FilterRequestDto;
import com.sparta.airbnb_clone.dto.request.HouseRequestDto;
import com.sparta.airbnb_clone.dto.response.*;
import com.sparta.airbnb_clone.jwt.TokenProvider;
import com.sparta.airbnb_clone.repository.*;
import com.sparta.airbnb_clone.repository.support.HouseRepositorySupport;
import com.sparta.airbnb_clone.shared.Category;
import com.sparta.airbnb_clone.shared.FacilityType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;
    private final HouseRepositorySupport houseRepositorySupport;
    private final ReviewRepository reviewRepository;
    private final FacilityRepository facilityRepository;
    private final HouseImgRepository houseImgRepository;
    private final MemberRepository memberRepository;

    private final TokenProvider tokenProvider;

    // 데이터 수집용 임시 API
    @Transactional
    public ResponseDto<?> createHouse(HouseRequestDto requestDto) {

        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원")
        );

        House house = House.builder()
                .host(member)
                .category(requestDto.getCategory())
                .title(requestDto.getTitle())
                .nation(requestDto.getNation())
                .address(requestDto.getAddress())
                .longitude(requestDto.getLongitude())
                .latitude(requestDto.getLatitude())
//                .starAvg()
                .descript(requestDto.getDescript())
                .price(requestDto.getPrice())
                .checkIn(requestDto.getCheckIn())
                .checkOut(requestDto.getCheckOut())
                .bedRoomCnt(requestDto.getBedRoomCnt())
                .bedCnt(requestDto.getBedCnt())
                .build();

        houseRepository.save(house);

        List<Facility> facilities = new ArrayList<>();

        for (FacilityType facilityType : requestDto.getFacilities()) {
            facilities.add(
                    Facility.builder()
                            .house(house)
                            .facilityType(facilityType)
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

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllHouses() {
        List<HouseMainResponseDto> houseMainResponseDtoList = houseRepositorySupport.findAllByOrderByModifiedAtDesc();
        return ResponseDto.success(houseMainResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllHousesByCategory(Category category) {
        List<HouseMainResponseDto> houseMainResponseDtoList = houseRepositorySupport.findAllByCategory(category);
        return ResponseDto.success(houseMainResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getHousesByFilter(FilterRequestDto requestDto) {


        List<HouseMainResponseDto> houseMainResponseDtoList = houseRepositorySupport.findAllByFilter(
                requestDto.getMinPrice(),
                requestDto.getMaxPrice(),
                requestDto.getBedRoomCnt(),
                requestDto.getBedCnt(),
                requestDto.getFacilities()
        );

        return ResponseDto.success(houseMainResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getHouseByHouseId(Long houseId) {

        House house = isPresentHouse(houseId);
        if (null == house) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<Facility> facilities = facilityRepository.findAllByHouse(house);
        List<FacilityType> facilityTypes = new ArrayList<>();

        for (Facility facility : facilities) {
            facilityTypes.add(facility.getFacilityType());
        }

        List<HouseImg> houseImgs = houseImgRepository.findAllByHouse(house);
        List<String> imgUrls = new ArrayList<>();

        for (HouseImg houseImg : houseImgs) {
            imgUrls.add(houseImg.getImgUrl());
        }

        List<Review> reviews = reviewRepository.findAllByHouse(house);
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviews) {
            reviewResponseDtoList.add(
                    ReviewResponseDto.builder()
                            .reviewId(review.getReviewId())
                            .author(review.getMember().getNickname())
                            .profileImgUrl(review.getMember().getProfileImgUrl())
                            .descript(review.getDescript())
                            .star(review.getStar())
                            .createdAt(review.getCreatedAt())
                            .build()
            );
        }

        return ResponseDto.success(
                HouseDetailResponseDto.builder()
                        .houseId(house.getHouseId())
                        .category(house.getCategory())
                        .host(
                                HostResponseDto.builder()
                                        .memberId(house.getHost().getMemberId())
                                        .nickname(house.getHost().getNickname())
                                        .profileImgUrl("")
                                        .reviewCnt(reviewRepository.countByHouse(house))
                                        .isSuperHost(house.getHost().getIsSuperHost())
                                        .createdAt(house.getCreatedAt())
                                        .build()
                        )
                        .title(house.getTitle())
                        .nation(house.getNation())
                        .address(house.getAddress())
                        .latitude(house.getLatitude())
                        .longitude(house.getLongitude())
                        .descript(house.getDescript())
                        .starAvg(house.getStarAvg())
                        .price(house.getPrice())
                        .checkIn(house.getCheckIn())
                        .checkOut(house.getCheckOut())
                        .bedRoomCnt(house.getBedRoomCnt())
                        .bedCnt(house.getBedCnt())
                        .facilities(facilityTypes)
                        .houseImgs(imgUrls)
                        .reviews(reviewResponseDtoList)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public House isPresentHouse(Long houseId) {
        Optional<House> optionalHouse = houseRepository.findById(houseId);
        return optionalHouse.orElse(null);
    }
}
