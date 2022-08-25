package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findById(Long id);
  Optional<Member> findByNickname(String nickname);
  Optional<Member> findByEmail(String email);
  Optional<Member> findByKakaoId(Long kakaoId);
}
