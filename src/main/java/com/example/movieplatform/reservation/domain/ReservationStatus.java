package com.example.movieplatform.reservation.domain;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    PENDING("결제 대기중"),
    PAID("결제 완료"),
    VIEW("관람 완료"),
    CANCELLED("예매 취소");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

}