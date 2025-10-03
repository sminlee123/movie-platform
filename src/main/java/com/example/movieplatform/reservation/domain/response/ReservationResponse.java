package com.example.movieplatform.reservation.domain.response;

import com.example.movieplatform.reservation.domain.Reservation;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        int totalPrice,
        LocalDateTime reservationDate,
        String status
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getFinalPrice(),
                reservation.getReservationDate(),
                reservation.getStatus().getDescription()
        );
    }
}
