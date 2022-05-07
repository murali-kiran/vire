package com.vire.service;

import com.vire.model.request.FeedLikesRequest;
import com.vire.model.response.FeedLikesResponse;
import com.vire.repository.FeedLikesRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedLikesService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  FeedLikesRepository feedLikesRepository;

  public FeedLikesResponse create(final FeedLikesRequest request) {

    var dto = request.toDto(snowflake);

    return FeedLikesResponse.fromDto(feedLikesRepository.create(dto));
  }

  public FeedLikesResponse update(final FeedLikesRequest request) {

    var dto = request.toDto();

    return FeedLikesResponse.fromDto(feedLikesRepository.update(dto));
  }

  public FeedLikesResponse delete(final Long feedLikesId) {

    return feedLikesRepository.delete(feedLikesId)
            .map(dto -> FeedLikesResponse.fromDto(dto))
            .get();
  }

  public List<FeedLikesResponse> getAll() {

    return feedLikesRepository
            .getAll()
            .stream()
            .map(dto -> FeedLikesResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public FeedLikesResponse retrieveById(Long feedLikesId) {

    return feedLikesRepository
            .retrieveById(feedLikesId)
            .map(dto -> FeedLikesResponse.fromDto(dto))
            .get();
  }

  public List<FeedLikesResponse> search(final String searchString) {

    return feedLikesRepository
            .search(searchString)
            .stream()
            .map(dto -> FeedLikesResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
