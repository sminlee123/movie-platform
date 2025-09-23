package com.example.movieplatform.reservation.service;

import com.example.movieplatform.reservation.domain.request.ReservationRequest;
import com.example.movieplatform.reservation.domain.response.ReservationDetailResponse;
import com.example.movieplatform.reservation.domain.response.ReservationResponse;
import com.example.movieplatform.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
    void createReservation(ReservationRequest request, User user);
    Page<ReservationResponse> getReservationsByUserId(Long userId, Pageable pageable);
    ReservationDetailResponse getReservationDetails(Long reservationId);
}
