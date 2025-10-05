package com.example.movieplatform.showinginfo.repository;

import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomShowingInfoRepository {
    // 이건 필요없을 거 같기도
    Optional<ShowingInfoResponse> getShowingInfoByShowingId(Long showingId);
    Page<ShowingInfoResponse> findAllShowingsByScreenId(Pageable pageable, Long screenId);
    List<ShowingInfoResponse> findShowingsByMovieId(Long movieId);
}
