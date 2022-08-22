package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.*;
import com.sparta.airbnb_clone.dto.request.HouseRequestDto;
import com.sparta.airbnb_clone.dto.response.HouseMainResponseDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.jwt.TokenProvider;
import com.sparta.airbnb_clone.repository.*;
import com.sparta.airbnb_clone.shared.FacilityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;
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
                .starAvg(0)
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
        List<House> houses = houseRepository.findAllByOrderByModifiedAtDesc();
        List<HouseMainResponseDto> houseMainResponseDtoList = new ArrayList<>();

        for (House house : houses) {
            houseMainResponseDtoList.add(
                    HouseMainResponseDto.builder()
                            .houseId(house.getHouseId())
                            .title(house.getTitle())
//                            .distance()
                            .price(house.getPrice())
                            .starAvg(house.getStarAvg())
                            .build()
            );
        }

        return ResponseDto.success(houseMainResponseDtoList);
    }

    @Transactional
    public Member validateMember() {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
