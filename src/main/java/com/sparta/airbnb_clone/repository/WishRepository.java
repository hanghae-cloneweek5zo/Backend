package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query(value = "select DISTINCT w from Wish w join fetch w.member where w.member.memberId > :memberId ORDER BY w.createdAt desc")
    List<Wish> findAllByMember(@Param(value = "memberId") Long memberId);

    Optional<Wish> findByHouseAndMember(House house, Member member);
}