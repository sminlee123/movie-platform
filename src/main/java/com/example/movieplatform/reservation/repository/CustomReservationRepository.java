package com.example.movieplatform.reservation.repository;

import com.example.movieplatform.reservation.domain.response.ReservationInfoTuple;
import com.example.movieplatform.reservation.domain.response.ReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomReservationRepository {
    Page<ReservationResponse> findAllReservationsByUserId(Long userId, Pageable pageable);

    Optional<ReservationInfoTuple> findReservationInfoById(Long reservationId);
}

