package com.vire.service;

import com.vire.model.request.FeedbackRequest;
import com.vire.model.response.FeedbackResponse;
import com.vire.repository.FeedbackRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  FeedbackRepository feedbackRepository;

  public FeedbackResponse create(final FeedbackRequest request) {

    var dto = request.toDto(snowflake);

    return FeedbackResponse.fromDto(feedbackRepository.create(dto));
  }

  public FeedbackResponse update(final FeedbackRequest request) {

    var dto = request.toDto();

    return FeedbackResponse.fromDto(feedbackRepository.update(dto));
  }

  public FeedbackResponse delete(final Long feedbackId) {

    return feedbackRepository.delete(feedbackId)
            .map(dto -> FeedbackResponse.fromDto(dto))
            .get();
  }

  public List<FeedbackResponse> getAll() {

    return feedbackRepository
            .getAll()
            .stream()
            .map(dto -> FeedbackResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public FeedbackResponse retrieveById(Long feedbackId) {

    return feedbackRepository
            .retrieveById(feedbackId)
            .map(dto -> FeedbackResponse.fromDto(dto))
            .get();
  }

  public List<FeedbackResponse> search(final String searchString) {

    return feedbackRepository
            .search(searchString)
            .stream()
            .map(dto -> FeedbackResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
