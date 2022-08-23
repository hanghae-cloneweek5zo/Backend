package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.HouseImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseImgRepository extends JpaRepository<HouseImg, Long> {
    List<HouseImg> findAllByHouse(House house);
}
