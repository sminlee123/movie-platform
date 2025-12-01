package com.example.movieplatform.screen.repository;

import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.domain.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, CustomSeatRepository {
    Optional<Seat> findByNameAndScreen(String name, Screen screen);
    Long countAllSeatsByScreen(Screen screen);
    void deleteAllByScreen(Screen screen);

    // 비관적 락 추가
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Seat> findByScreenAndIdIn(Screen screen, List<Long> seatIds);
}
