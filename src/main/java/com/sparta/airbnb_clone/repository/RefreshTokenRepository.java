package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}
