//package com.sparta.airbnb_clone.repository;
//
//import com.sparta.woonha99.domain.*;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//
//public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
//    Optional<PostLike> findByMemberAndPost(Member member, Post post);
//    Long countByPost(Post post);
//}
