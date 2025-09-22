package com.example.movieplatform.reservation.domain.response;

import com.example.movieplatform.reservation.domain.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        int totalPrice,
        LocalDateTime reservationDate,
        ReservationStatus status
) {
    public String getStatusDescription() {
        return status.getDescription();
    }
}
