package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.House;
import com.sparta.airbnb_clone.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByHouseOrderByCreatedAtDesc(House house);

    List<Review> findAllByHouse(House house);
    int countByHouse(House house);

}
