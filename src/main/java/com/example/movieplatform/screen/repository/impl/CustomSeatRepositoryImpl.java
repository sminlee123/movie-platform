package com.example.movieplatform.screen.repository.impl;

import com.example.movieplatform.screen.repository.CustomSeatRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.movieplatform.screen.domain.QSeat.seat;

@RequiredArgsConstructor
public class CustomSeatRepositoryImpl implements CustomSeatRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, Long> findTotalCountsByScreenIds(List<Long> screenIds) {
        return queryFactory
                .select(seat.screen.id, seat.count())
                .from(seat)
                .where(seat.screen.id.in(screenIds))
                .groupBy(seat.screen.id) // List<Tuple> 형태 반환
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(seat.screen.id),
                        tuple -> tuple.get(seat.count())
                ));
    }
}
