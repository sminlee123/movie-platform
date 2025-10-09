package com.example.movieplatform.showinginfo.service;

import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.showinginfo.domain.request.ShowingInfoCreateRequest;
import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import com.example.movieplatform.showinginfo.domain.response.ShowingSeatsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShowingInfoService {
    Long createShowingInfo(ShowingInfoCreateRequest request);
    Page<ShowingInfoResponse> getShowingInfos(Pageable pageable, Long screenId);
    List<ShowingInfoResponse> getShowingInfosByMovieId(Long movieId);
    Long deleteShowingInfo(Long showingInfoId);
    ShowingInfo validateShowingInfo(Long showingInfoId);
    ShowingSeatsResponse getShowingSeats(Long showingInfoId);
}
