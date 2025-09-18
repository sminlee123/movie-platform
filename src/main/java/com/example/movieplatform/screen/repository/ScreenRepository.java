package com.example.movieplatform.screen.repository;

import com.example.movieplatform.screen.domain.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long>, CustomScreenRepository {
    boolean existsByName(String name);
    boolean existsById(Long id);
    Optional<Screen> findById(Long id);
}
