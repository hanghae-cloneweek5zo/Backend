package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.Wish;
import com.sparta.airbnb_clone.dto.request.WishRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.dto.response.WishResponseDto;
import com.sparta.airbnb_clone.repository.HouseRepository;
import com.sparta.airbnb_clone.repository.MemberRepository;
import com.sparta.airbnb_clone.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {

    private final HouseRepository houseRepository;
    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;


    @Transactional
    public ResponseDto<?> createWish(WishRequestDto requestDto){
        Member host = isPresentMember(requestDto.getHostId());
        if(host == null){
            return ResponseDto.fail("HOST_ID_NULL","호스트 아이디가 없습니다.");
        }
        House house = isPresentHouse(requestDto.getHouseId());
        if(house == null){
            return ResponseDto.fail("HOUSE_ID_NULL","하우스의 아이디가 존재하지 않습니다.");
        }
        Wish wish = new Wish(house,host);
        wishRepository.save(wish);

        return ResponseDto.success(new WishResponseDto(wish,"위시리스트에 추가되었습니다."));

    }



    @Transactional(readOnly = true)
    public ResponseDto<?> getWishes(Long hostId){


    }

    @Transactional
    public ResponseDto<?> deleteWishes(Long houseId){


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
