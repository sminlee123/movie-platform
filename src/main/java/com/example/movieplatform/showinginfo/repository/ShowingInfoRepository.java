package com.example.movieplatform.showinginfo.repository;

import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ShowingInfoRepository extends JpaRepository<ShowingInfo, Long>, CustomShowingInfoRepository {
    Optional<ShowingInfo> findById(Long Id);
    boolean existsByScreenAndShowingDate(Screen screen, LocalDate showingDate);
}
