package com.example.movieplatform.ticket.repository.impl;

import com.example.movieplatform.reservation.domain.ReservationStatus;
import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.ticket.repository.CustomTicketRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.movieplatform.reservation.domain.QReservation.reservation;
import static com.example.movieplatform.screen.domain.QSeat.seat;
import static com.example.movieplatform.ticket.domain.QTicket.ticket;

@Slf4j
@RequiredArgsConstructor
public class CustomTicketRepositoryImpl implements CustomTicketRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, Long> findBookedCountsByShowingInfoIds(List<Long> showingInfoIds) {

        // Tuple 여러 타입의 데이터를 담을 수 있는 임시 보관 상자
        List<Tuple> tuples = queryFactory
                .select(ticket.showingInfo.id, ticket.count())
                .from(ticket)
                .join(ticket.reservation, reservation)
                .where(ticket.showingInfo.id.in(showingInfoIds),
                        reservation.status.ne(ReservationStatus.CANCELLED)) // TODO 일단 취소 아닌거는 다 카운팅
                .groupBy(ticket.showingInfo.id)
                .fetch();

        return tuples.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(ticket.showingInfo.id),
                        tuple -> tuple.get(ticket.count())
                ));
    }

    @Override
    public List<String> findSeatNameByReservationId(Long reservationId) {
        return queryFactory
                .select(seat.name)
                .from(ticket)
                .join(ticket.seat, seat)
                .where(ticket.reservation.id.eq(reservationId))
                .orderBy(seat.name.asc())
                .fetch();
    }

    @Override
    public boolean validateTicketForReservation(ShowingInfo showingInfo, List<Seat> seats) {
        Integer valid = queryFactory
                .selectOne()
                .from(ticket)
                .join(ticket.reservation, reservation)
                .where(ticket.showingInfo.eq(showingInfo),
                        ticket.seat.in(seats),
                        reservation.status.ne(ReservationStatus.CANCELLED))
                .fetchFirst();

        return valid != null;
    }
}
