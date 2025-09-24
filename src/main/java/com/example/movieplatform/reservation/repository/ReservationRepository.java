package com.example.movieplatform.reservation.repository;

import com.example.movieplatform.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, CustomReservationRepository {
    Optional<Reservation> findByid(Long id);
}
