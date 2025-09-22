package com.example.movieplatform.screen.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomSeatRepository {
    Map<Long, Long> findTotalCountsByScreenIds(List<Long> screenIds);

}
