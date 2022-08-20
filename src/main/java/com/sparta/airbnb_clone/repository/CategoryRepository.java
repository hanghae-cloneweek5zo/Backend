package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Category;
import com.sparta.airbnb_clone.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
