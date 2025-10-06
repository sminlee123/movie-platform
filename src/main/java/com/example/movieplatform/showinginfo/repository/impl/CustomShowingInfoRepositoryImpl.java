package com.example.movieplatform.showinginfo.repository.impl;

import com.example.movieplatform.reservation.domain.Reservation;
import com.example.movieplatform.reservation.domain.ReservationStatus;
import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.domain.response.ShowingSeatsResponse;
import com.example.movieplatform.showinginfo.repository.CustomShowingInfoRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.movieplatform.movie.domain.QMovie.movie;
import static com.example.movieplatform.reservation.domain.QReservation.reservation;
import static com.example.movieplatform.screen.domain.QScreen.screen;
import static com.example.movieplatform.screen.domain.QSeat.seat;
import static com.example.movieplatform.showinginfo.domain.QShowingInfo.showingInfo;
import static com.example.movieplatform.ticket.domain.QTicket.ticket;

@RequiredArgsConstructor
public class CustomShowingInfoRepositoryImpl implements CustomShowingInfoRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ShowingInfoResponse> getShowingInfoByShowingId(Long showingId) {
        ShowingInfoResponse response = queryFactory
                .select(Projections.constructor(ShowingInfoResponse.class,
                        showingInfo.id,
                        movie.title,
                        screen.name,
                        showingInfo.showingDate,
                        showingInfo.startTime,
                        showingInfo.endTime,
                        showingInfo.price
                ))
                .from(showingInfo)
                .join(showingInfo.movie, movie)
                .join(showingInfo.screen, screen)
                .where(showingInfo.id.eq(showingId))
                .fetchOne();

        return Optional.ofNullable(response);
    }

    @Override
    public Page<ShowingInfoResponse> findAllShowingsByScreenId(Pageable pageable, Long screenId) {
        List<ShowingInfoResponse> content = queryFactory
                .select(Projections.constructor(ShowingInfoResponse.class,
                        showingInfo.id,
                        movie.title,
                        screen.name,
                        showingInfo.showingDate,
                        showingInfo.startTime,
                        showingInfo.endTime,
                        showingInfo.price
                ))
                .from(showingInfo)
                .join(showingInfo.movie, movie)
                .join(showingInfo.screen, screen)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(showingInfo.screen.id.eq(screenId))
                .orderBy(showingInfo.id.desc())
                .fetch();

        Long total = queryFactory
                .select(showingInfo.count())
                .from(showingInfo)
                .where(showingInfo.screen.id.eq(screenId))
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public List<ShowingInfoResponse> findShowingsByMovieId(Long movieId) {
        // 현재부터 +3일까지 상영정보만 가져옴
        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(3);

        return queryFactory
                .select(Projections.constructor(ShowingInfoResponse.class,
                        showingInfo.id,
                        movie.title,
                        screen.name,
                        showingInfo.showingDate,
                        showingInfo.startTime,
                        showingInfo.endTime,
                        showingInfo.price
                ))
                .from(showingInfo)
                .join(showingInfo.movie, movie)
                .join(showingInfo.screen, screen)
                .where(
                        showingInfo.movie.id.eq(movieId),
                        showingInfo.showingDate.between(today, threeDaysLater)
                )
                .orderBy(
                        showingInfo.showingDate.asc(),
                        showingInfo.startTime.asc()
                )
                .fetch();
    }

    @Override
    public Map<Long, Long> findTotalCountsByShowingInfoIds(List<Long> showingInfoIds) {
        return queryFactory
                .select(showingInfo.id, seat.count())
                .from(showingInfo)
                .join(showingInfo.screen, screen)
                .join(seat).on(seat.screen.eq(screen))
                .where(showingInfo.id.in(showingInfoIds))
                .groupBy(showingInfo.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple ->  tuple.get(showingInfo.id),
                        tuple -> tuple.get(seat.count())
                ));

    }

    @Override
    public Optional<ShowingSeatsResponse> findShowingSeatsByShowingInfoId(Long showingInfoId) {
        Screen screenInfo = queryFactory
                .select(showingInfo.screen)
                .from(showingInfo)
                .where(showingInfo.id.eq(showingInfoId))
                .fetchOne();

        if (screenInfo == null) {
            return Optional.empty();
        }

        // 해당 스크린의 행과 열
        int maxRow = queryFactory
                .select(seat.rowNumber.max())
                .from(seat)
                .where(seat.screen.id.eq(screenInfo.getId()))
                .fetchOne();

        int maxCol = queryFactory
                .select(seat.colNumber.max())
                .from(seat)
                .where(seat.screen.id.eq(screenInfo.getId()))
                .fetchOne();

        // 예매된 좌석 아이디 (취소 상태 x)
        List<Long> reservationSeatIds = queryFactory
                .select(ticket.seat.id)
                .from(ticket)
                .join(ticket.reservation, reservation)
                .where(
                        ticket.showingInfo.id.eq(showingInfoId),
                        reservation.status.ne(ReservationStatus.CANCELLED)
                )
                .fetch();

        // 임시 좌석 데이터
        List<Tuple> tempSeatData = queryFactory
                .select(
                        seat.id,
                        seat.rowNumber,
                        seat.colNumber
                )
                .from(seat)
                .where(seat.screen.id.eq(screenInfo.getId()))
                .fetch();

        // dto 변환
        List<ShowingSeatsResponse.SeatInfo> seats = tempSeatData.stream()
                .map(tuple -> {
                    Long seatId = tuple.get(seat.id);
                    int row = tuple.get(seat.rowNumber);
                    int column = tuple.get(seat.colNumber);

                    ShowingSeatsResponse.SeatStatus status = reservationSeatIds.contains(seatId)
                            ? ShowingSeatsResponse.SeatStatus.NOT_AVAILABLE
                            : ShowingSeatsResponse.SeatStatus.AVAILABLE;

                    return new ShowingSeatsResponse.SeatInfo(
                            seatId,
                            row,
                            column,
                            status
                    );
                })
                .collect(Collectors.toList());

        return Optional.of(new ShowingSeatsResponse(
                screenInfo.getId(),
                screenInfo.getName(),
                maxRow,
                maxCol,
                seats
        ));
    }
}
