package com.example.movieplatform.ticket.domain;

import com.example.movieplatform.reservation.domain.Reservation;
import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showing_info_id")
    private ShowingInfo showingInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    protected Ticket(ShowingInfo showingInfo, Seat seat, Reservation reservation) {
        this.showingInfo = showingInfo;
        this.seat = seat;
        this.reservation = reservation;
    }

    public static Ticket create(ShowingInfo showingInfo, Seat seat, Reservation reservation) {
        return new Ticket(showingInfo, seat, reservation);
    }
}
