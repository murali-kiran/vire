package com.vire.service;

import com.vire.model.request.FeedReportRequest;
import com.vire.model.response.FeedReportResponse;
import com.vire.repository.FeedReportRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedReportService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  FeedReportRepository feedReportRepository;

  public FeedReportResponse create(final FeedReportRequest request) {

    var dto = request.toDto(snowflake);

    return FeedReportResponse.fromDto(feedReportRepository.create(dto));
  }

  public FeedReportResponse update(final FeedReportRequest request) {

    var dto = request.toDto();

    return FeedReportResponse.fromDto(feedReportRepository.update(dto));
  }

  public FeedReportResponse delete(final Long feedReportId) {

    return feedReportRepository.delete(feedReportId)
            .map(dto -> FeedReportResponse.fromDto(dto))
            .get();
  }

  public List<FeedReportResponse> getAll() {

    return feedReportRepository
            .getAll()
            .stream()
            .map(dto -> FeedReportResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public FeedReportResponse retrieveById(Long feedReportId) {

    return feedReportRepository
            .retrieveById(feedReportId)
            .map(dto -> FeedReportResponse.fromDto(dto))
            .get();
  }

  public List<FeedReportResponse> search(final String searchString) {

    return feedReportRepository
            .search(searchString)
            .stream()
            .map(dto -> FeedReportResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
