package com.example.movieplatform.reservation.service.impl;

import com.example.movieplatform.reservation.domain.Reservation;
import com.example.movieplatform.reservation.domain.request.ReservationRequest;
import com.example.movieplatform.reservation.repository.ReservationRepository;
import com.example.movieplatform.reservation.service.ReservationService;
import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.screen.exception.SeatNotAvailableException;
import com.example.movieplatform.screen.exception.SeatNotFoundException;
import com.example.movieplatform.screen.repository.SeatRepository;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.showinginfo.exception.ShowingInfoNotExistsException;
import com.example.movieplatform.showinginfo.repository.ShowingInfoRepository;
import com.example.movieplatform.ticket.repository.TicketRepository;
import com.example.movieplatform.ticket.service.TicketService;
import com.example.movieplatform.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShowingInfoRepository showingInfoRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    @Override
    public void createReservation(ReservationRequest request, User user) {
        log.info("Seat id {}", request.seatIds());


        // 상영정보 존재 유무 체크
        ShowingInfo showingInfo = showingInfoRepository.findById(request.showingInfoId())
                .orElseThrow(ShowingInfoNotExistsException::new);

        // 좌석 존재 유무 체크
        List<Seat> seats = seatRepository.findByScreenAndIdIn(showingInfo.getScreen(), request.seatIds());

        if (seats.size() != request.seatIds().size()) {
            throw new SeatNotFoundException();
        }

        // 예매 유무 체크
        if (ticketRepository.existsByShowingInfoAndSeatIn(showingInfo, seats)) {
            throw new SeatNotAvailableException();
        }

        int totalPrice = seats.size() * showingInfo.getPrice();

        // 예약 및 티켓 생성
        Reservation reservation = Reservation.create(user, totalPrice);
        Reservation savedReservation = reservationRepository.save(reservation);

        ticketService.createAndAddTicketsToReservation(savedReservation, showingInfo, seats);

        log.info("Reservation has been created : {}", reservation);
    }
}
