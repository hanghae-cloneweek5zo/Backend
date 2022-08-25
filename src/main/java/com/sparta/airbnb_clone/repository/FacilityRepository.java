package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Facility;
import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.shared.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findAllByHouse(House house);
}
