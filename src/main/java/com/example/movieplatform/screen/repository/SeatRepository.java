package com.example.movieplatform.screen.repository;

import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, CustomSeatRepository {
    Optional<Seat> findByNameAndScreen(String name, Screen screen);
    Long countAllSeatsByScreen(Screen screen);
    void deleteAllByScreen(Screen screen);
}
