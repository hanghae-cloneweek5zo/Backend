package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Optional<Review> findByReviewId(Long reviewId);

    List<Review> findAllBy();

    List<Review> findAllByHouseOrderByCreatedAtDesc(Long accId);
}
