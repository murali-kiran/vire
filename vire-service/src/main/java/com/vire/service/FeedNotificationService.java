package com.vire.service;

import com.vire.model.request.FeedNotificationRequest;
import com.vire.model.response.FeedNotificationResponse;
import com.vire.repository.FeedNotificationRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedNotificationService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  FeedNotificationRepository feedNotificationRepository;

  public FeedNotificationResponse create(final FeedNotificationRequest request) {

    var dto = request.toDto(snowflake);

    return FeedNotificationResponse.fromDto(feedNotificationRepository.create(dto));
  }

  public FeedNotificationResponse update(final FeedNotificationRequest request) {

    var dto = request.toDto();

    return FeedNotificationResponse.fromDto(feedNotificationRepository.update(dto));
  }

  public FeedNotificationResponse delete(final Long feedNotificationId) {

    return feedNotificationRepository.delete(feedNotificationId)
            .map(dto -> FeedNotificationResponse.fromDto(dto))
            .get();
  }

  public List<FeedNotificationResponse> getAll() {

    return feedNotificationRepository
            .getAll()
            .stream()
            .map(dto -> FeedNotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public FeedNotificationResponse retrieveById(Long feedNotificationId) {

    return feedNotificationRepository
            .retrieveById(feedNotificationId)
            .map(dto -> FeedNotificationResponse.fromDto(dto))
            .get();
  }

  public List<FeedNotificationResponse> search(final String searchString) {

    return feedNotificationRepository
            .search(searchString)
            .stream()
            .map(dto -> FeedNotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
