package com.example.movieplatform.reservation.repository.impl;

import com.example.movieplatform.reservation.domain.response.ReservationResponse;
import com.example.movieplatform.reservation.repository.CustomReservationRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.movieplatform.reservation.domain.QReservation.reservation;

@RequiredArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReservationResponse> findAllReservationsByUserId(Long userId, Pageable pageable) {
        List<ReservationResponse> content = queryFactory
                .select(Projections.constructor(ReservationResponse.class,
                        reservation.id,
                        reservation.finalPrice,
                        reservation.reservationDate
                        ))
                .from(reservation)
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

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }
}
