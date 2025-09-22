package com.example.movieplatform.reservation.domain;

public enum ReservationStatus {
    PENDING,    // 결제 대기중 (진행중)
    PAID,       // 결제 완료
    CANCELLED   // 취소됨
}