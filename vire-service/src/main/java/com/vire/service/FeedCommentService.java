package com.vire.service;

import com.vire.model.request.FeedCommentRequest;
import com.vire.model.response.FeedCommentResponse;
import com.vire.repository.FeedCommentRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedCommentService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  FeedCommentRepository feedCommentRepository;

  public FeedCommentResponse create(final FeedCommentRequest request) {

    var dto = request.toDto(snowflake);

    return FeedCommentResponse.fromDto(feedCommentRepository.create(dto));
  }

  public FeedCommentResponse update(final FeedCommentRequest request) {

    var dto = request.toDto();

    return FeedCommentResponse.fromDto(feedCommentRepository.update(dto));
  }

  public FeedCommentResponse delete(final Long feedCommentId) {

    return feedCommentRepository.delete(feedCommentId)
            .map(dto -> FeedCommentResponse.fromDto(dto))
            .get();
  }

  public List<FeedCommentResponse> getAll() {

    return feedCommentRepository
            .getAll()
            .stream()
            .map(dto -> FeedCommentResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public FeedCommentResponse retrieveById(Long feedCommentId) {

    return feedCommentRepository
            .retrieveById(feedCommentId)
            .map(dto -> FeedCommentResponse.fromDto(dto))
            .get();
  }

  public List<FeedCommentResponse> search(final String searchString) {

    return feedCommentRepository
            .search(searchString)
            .stream()
            .map(dto -> FeedCommentResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
