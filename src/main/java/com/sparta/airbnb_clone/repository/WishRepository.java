package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("select DISTINCT w from Wish w join fetch w.host ORDER BY w.createdAt desc")
    List<Wish> findAllByHouse(Member member);

//    List<Wish> findAllOrderByHouse(Long hostId);

    Optional<Wish> findByHouseAndHost(House house, Member host);

}