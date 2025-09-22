package com.example.movieplatform.showinginfo.service;

import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.showinginfo.domain.request.ShowingInfoCreateRequest;
import com.example.movieplatform.showinginfo.domain.response.ShowingInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShowingInfoService {
    Long createShowingInfo(ShowingInfoCreateRequest request);
    Page<ShowingInfoResponse> getShowingInfos(Pageable pageable, Long screenId);
    Long deleteShowingInfo(Long showingInfoId);
    ShowingInfo validateShowingInfo(Long showingInfoId);
}
