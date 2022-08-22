package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Category;
import com.sparta.airbnb_clone.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

}
