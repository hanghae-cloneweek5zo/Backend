package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.Wish;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.dto.response.WishResponseDto;
import com.sparta.airbnb_clone.repository.HouseRepository;
import com.sparta.airbnb_clone.repository.MemberRepository;
import com.sparta.airbnb_clone.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final HouseRepository houseRepository;
    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;
    @Transactional
    public ResponseDto<?> toggleWishByHouse(Long hostId,Long houseId){
        Member host = isPresentMember(hostId);
        if(host == null){
            return ResponseDto.fail("HOST_ID_NULL","호스트 아이디가 없습니다.");
        }
        House house = isPresentHouse(houseId);
        if(house == null){
            return ResponseDto.fail("HOUSE_ID_NULL","하우스의 아이디가 존재하지 않습니다.");
        }

        Wish wish = isPresentWishByHouse(house,host);
        //좋아요 안누름
        if(wish == null){
            Wish tempWish = new Wish(house,host);
            wishRepository.save(tempWish);
            return ResponseDto.success(new WishResponseDto(tempWish,"위시리스트에 추가되었습니다."));
        }else{
            //좋아요 누름
            wishRepository.delete(wish);
            return ResponseDto.success(new WishResponseDto(wish,"위시리스트가 삭제되었습니다."));
        }
    }
    @Transactional(readOnly = true)
    public ResponseDto<?> getWishes(Long hostId){
        List<Wish> wishList = wishRepository.findAllOrderByHouse(hostId);
        ArrayList<WishResponseDto> wishResponseDtoArrayList = new ArrayList<>();
        for(Wish temp : wishList){
            wishResponseDtoArrayList.add(new WishResponseDto(temp));
        }
        return  ResponseDto.success(wishResponseDtoArrayList);
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

    @Transactional(readOnly = true)
    public Wish isPresentWishByHouse(House house,Member host){
        Optional<Wish> optionalWish = wishRepository.findByHouseAndHost(house,host);
        return optionalWish.orElse(null);
    }
}
