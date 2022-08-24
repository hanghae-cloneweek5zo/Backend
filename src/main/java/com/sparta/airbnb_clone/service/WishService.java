package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.HouseImg;
import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.Wish;
import com.sparta.airbnb_clone.dto.response.HouseMainResponseDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.dto.response.WishResponseDto;
import com.sparta.airbnb_clone.jwt.TokenProvider;
import com.sparta.airbnb_clone.repository.HouseImgRepository;
import com.sparta.airbnb_clone.repository.HouseRepository;
import com.sparta.airbnb_clone.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final HouseRepository houseRepository;
    private final TokenProvider tokenProvider;
    private final WishRepository wishRepository;
    private final HouseImgRepository houseImgRepository;

    @Transactional
    public ResponseDto<?> toggleWishByHouse(HttpServletRequest request, Long houseId) {
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
            return ResponseDto.fail("HOUSE_ID_NULL", "하우스의 아이디가 존재하지 않습니다.");
        }

        Wish wish = isPresentWishByHouse(house, member);
        //좋아요 안누름
        if (wish == null) {
            Wish tempWish = new Wish(house, member);
            wishRepository.save(tempWish);
            return ResponseDto.success(new WishResponseDto(tempWish, "위시리스트에 추가되었습니다."));
        } else {
            //좋아요 누름
            wishRepository.delete(wish);
            return ResponseDto.success(new WishResponseDto(wish, "위시리스트가 삭제되었습니다."));
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getWishes(HttpServletRequest request) {
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        Member member = validateMember();
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        List<Wish> wishList = wishRepository.findAllByMember(member);
        List<House> houseList;
        ArrayList<HouseMainResponseDto> wishResponseDtoArrayList = new ArrayList<>();
        for (Wish temp : wishList) {
            houseList = houseRepository.findAllByHouseId(temp.getHouse().getHouseId());
            for(House h : houseList){
                List<HouseImg> houseImgs = houseImgRepository.findAllByHouse(h);

                wishResponseDtoArrayList.add(new HouseMainResponseDto(
                        h.getHouseId(),
                        h.getCategory(),
                        h.getTitle(),
                        h.getNation(),
                        h.getPrice(),
                        h.getStarAvg(),
                        houseImgs.get(0).getImgUrl()
                ));
            }

        }
        return ResponseDto.success(wishResponseDtoArrayList);
//        List<Wish> wishList = wishRepository.findAllByHouse(member);
//        ArrayList<WishResponseDto> wishResponseDtoArrayList = new ArrayList<>();
//        for(Wish temp : wishList){
//            wishResponseDtoArrayList.add(new WishResponseDto(temp,"조회 완료"));
//        }
//        return  ResponseDto.success(wishResponseDtoArrayList);
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

    @Transactional(readOnly = true)
    public Wish isPresentWishByHouse(House house, Member member) {
        Optional<Wish> optionalWish = wishRepository.findByHouseAndMember(house, member);
        return optionalWish.orElse(null);
    }
}
