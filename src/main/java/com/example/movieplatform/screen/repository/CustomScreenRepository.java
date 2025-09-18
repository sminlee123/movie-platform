package com.example.movieplatform.screen.repository;

import com.example.movieplatform.screen.domain.response.ScreenResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomScreenRepository {
    Page<ScreenResponse> findAllScreens(Pageable pageable);
    Optional<ScreenResponse> responseFindById(Long id);
}
