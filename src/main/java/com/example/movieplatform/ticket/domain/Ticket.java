package com.example.movieplatform.ticket.domain;

import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.ticket.domain.request.TicketBuyRequest;
import com.example.movieplatform.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tickets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"showing_info_id", "seat_id"})
)
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

    // TODO 유저를 여기에 넣을가?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket(ShowingInfo showingInfo, Seat seat, User user) {
        this.showingInfo = showingInfo;
        this.seat = seat;
        this.user = user;
    }
}
