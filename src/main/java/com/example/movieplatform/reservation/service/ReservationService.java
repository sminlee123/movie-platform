package com.example.movieplatform.reservation.service;

import com.example.movieplatform.reservation.domain.request.ReservationRequest;
import com.example.movieplatform.user.domain.User;

public interface ReservationService {
    void createReservation(ReservationRequest request, User user);
}
