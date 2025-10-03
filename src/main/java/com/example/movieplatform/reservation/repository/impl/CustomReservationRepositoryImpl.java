package com.example.movieplatform.reservation.repository.impl;

import com.example.movieplatform.reservation.domain.Reservation;
import com.example.movieplatform.reservation.domain.response.ReservationInfoTuple;
import com.example.movieplatform.reservation.domain.response.ReservationResponse;
import com.example.movieplatform.reservation.repository.CustomReservationRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.movieplatform.movie.domain.QMovie.movie;
import static com.example.movieplatform.reservation.domain.QReservation.reservation;
import static com.example.movieplatform.screen.domain.QScreen.screen;
import static com.example.movieplatform.showinginfo.domain.QShowingInfo.showingInfo;
import static com.example.movieplatform.ticket.domain.QTicket.ticket;

@RequiredArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReservationResponse> findAllReservationsByUserId(Long userId, Pageable pageable) {
        List<Reservation> reservations = queryFactory
                .selectFrom(reservation)
                .where(reservation.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reservation.reservationDate.desc())
                .fetch();

        Long total = queryFactory
                .select(reservation.count())
                .from(reservation)
                .where(reservation.user.id.eq(userId))
                .fetchOne();

        List<ReservationResponse> content = reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public Optional<ReservationInfoTuple> findReservationInfoById(Long reservationId) {
        ReservationInfoTuple result = queryFactory.
                select(Projections.constructor(ReservationInfoTuple.class,
                        reservation.id,
                        reservation.status,
                        reservation.reservationDate,
                        reservation.finalPrice,
                        movie.title,
                        movie.posterUrl,
                        screen.name,
                        showingInfo.showingDate,
                        showingInfo.startTime,
                        showingInfo.endTime
                        ))
                .from(ticket)
                .join(ticket.reservation, reservation)
                .join(ticket.showingInfo, showingInfo)
                .join(showingInfo.movie, movie)
                .join(showingInfo.screen, screen)
                .where(reservation.id.eq(reservationId))
                .fetchFirst();

        return Optional.ofNullable(result);
    }
}
