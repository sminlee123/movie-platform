package com.example.movieplatform.showinginfo.repository;

import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.domain.response.ShowingSeatsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CustomShowingInfoRepository {

    Optional<ShowingInfoResponse> getShowingInfoByShowingId(Long showingId);
    Page<ShowingInfoResponse> findAllShowingsByScreenId(Pageable pageable, Long screenId);
    List<ShowingInfoResponse> findShowingsByMovieId(Long movieId);
    Map<Long, Long> findTotalCountsByShowingInfoIds(List<Long> showingInfoIds);
    Optional<ShowingSeatsResponse> findShowingSeatsByShowingInfoId(Long showingInfoId);
}
