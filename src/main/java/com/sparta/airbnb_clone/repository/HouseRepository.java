package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByOrderByModifiedAtDesc();
}
