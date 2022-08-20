package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
