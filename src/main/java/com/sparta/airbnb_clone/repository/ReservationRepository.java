package com.sparta.airbnb_clone.repository;

import com.sparta.airbnb_clone.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
