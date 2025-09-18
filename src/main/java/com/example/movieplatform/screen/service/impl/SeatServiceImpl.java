package com.example.movieplatform.screen.service.impl;

import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.screen.domain.request.SeatDeleteRequest;
import com.example.movieplatform.screen.domain.request.SeatGenerateRequest;
import com.example.movieplatform.screen.exception.ScreenNotFoundException;
import com.example.movieplatform.screen.exception.SeatNotAvailableException;
import com.example.movieplatform.screen.repository.ScreenRepository;
import com.example.movieplatform.screen.repository.SeatRepository;
import com.example.movieplatform.screen.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;

    @Override
    public void generateSeats(SeatGenerateRequest request) {
        Screen screen = screenRepository.findById(request.screenId())
                .orElseThrow(ScreenNotFoundException::new);

        // 좌석 리스트
        List<Seat> seats = new ArrayList<Seat>();

        // 좌석 생성
        for(int i = 0; i < request.rows(); i++) {
            char rowChar = (char) ('A' + i);
            for(int j = 1; j <= request.cols(); j++) {
                String seatName = rowChar + String.valueOf(j);
                Seat seat = new Seat(seatName, screen);
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
    }

    @Override
    public void generateSeats(Long screenId, int rows, int cols) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(ScreenNotFoundException::new);

        // 좌석 리스트
        List<Seat> seats = new ArrayList<Seat>();

        // 좌석 생성
        for(int i = 0; i < rows; i++) {
            char rowChar = (char) ('A' + i);
            for(int j = 1; j <= cols; j++) {
                String seatName = rowChar + String.valueOf(j);
                Seat seat = new Seat(seatName, screen);
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
    }

    // TODO 좌석 구매 시 상태 변경하려는 로직 (서비스 메서드 이름 고민중)
    @Override
    public void deleteSeats(SeatDeleteRequest request) {
        Screen screen = screenRepository.findById(request.screenId())
                .orElseThrow(ScreenNotFoundException::new);

        Seat seat = seatRepository.findByNameAndScreen(request.seatName(), screen)
                .orElseThrow(ScreenNotFoundException::new);

        if(!seat.getAvailable()) {
            throw new SeatNotAvailableException();
        }

        // 상태변경
        seat.updateAvailability(false);

        log.info("좌석 예매 완료 {}", request.seatName());
    }

    @Override
    public Long countAllSeats(Long screenId) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(ScreenNotFoundException::new);

        return seatRepository.countAllSeatsByScreen(screen);
    }

    @Override
    public Long countAvailableSeats(Long screenId) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(ScreenNotFoundException::new);

        return seatRepository.countAvailableSeatsByScreen(screen);
    }
}
