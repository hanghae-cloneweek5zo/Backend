package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}
