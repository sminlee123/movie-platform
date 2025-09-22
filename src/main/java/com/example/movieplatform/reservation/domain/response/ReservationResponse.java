package com.example.movieplatform.reservation.domain.response;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        int totalPrice,
        LocalDateTime reservationDate
) {
}
