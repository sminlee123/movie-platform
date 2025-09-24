package com.example.movieplatform.reservation.domain.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record ReservationDetailResponse(
        Long reservationId,
        String reservationStatus, // "결제 완료" 등 한글 상태
        LocalDateTime reservationDate,
        int finalPrice,
        String movieTitle,
        String posterUrl,
        String screenName,
        LocalDate showingDate,
        LocalTime startTime,
        LocalTime endTime,
        List<String> seatNames // ["A1", "A2", "A3"]
) {}
