package com.example.movieplatform.reservation.domain;

import com.example.movieplatform.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private LocalDateTime reservationDate;

    @Column(nullable = false)
    private int finalPrice;

    protected Reservation(User user, ReservationStatus reservationStatus, int finalPrice) {
        this.user = user;
        this.status = reservationStatus;
        this.finalPrice = finalPrice;
    }

    public static Reservation create(
            User user,
            int finalPrice) {
        return new Reservation(user, ReservationStatus.PENDING, finalPrice);
    }

    // 엔티티가 데이터베이스에 저장되기 직전에 호출됨
    @PrePersist
    public void prePersist() {
        this.reservationDate = LocalDateTime.now();
    }
}
