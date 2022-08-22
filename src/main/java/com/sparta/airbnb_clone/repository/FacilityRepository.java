package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
