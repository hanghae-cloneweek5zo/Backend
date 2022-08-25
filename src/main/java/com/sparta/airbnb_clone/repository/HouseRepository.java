package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByOrderByModifiedAtDesc();
    List<House> findAllByHost(Member host);
    List<House> findAllByHouseId(Long houseId);

}
