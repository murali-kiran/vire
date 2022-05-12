package com.vire.service;

import com.vire.model.request.ExperienceCommentRequest;
import com.vire.model.response.ExperienceCommentResponse;
import com.vire.repository.ExperienceCommentRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceCommentService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ExperienceCommentRepository experienceCommentRepository;

  public ExperienceCommentResponse create(final ExperienceCommentRequest request) {

    var dto = request.toDto(snowflake);

    return ExperienceCommentResponse.fromDto(experienceCommentRepository.create(dto));
  }

  public ExperienceCommentResponse update(final ExperienceCommentRequest request) {

    var dto = request.toDto();

    return ExperienceCommentResponse.fromDto(experienceCommentRepository.update(dto));
  }

  public ExperienceCommentResponse delete(final Long experienceCommentId) {

    return experienceCommentRepository.delete(experienceCommentId)
            .map(dto -> ExperienceCommentResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceCommentResponse> getAll() {

    return experienceCommentRepository
            .getAll()
            .stream()
            .map(dto -> ExperienceCommentResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ExperienceCommentResponse retrieveById(Long experienceCommentId) {

    return experienceCommentRepository
            .retrieveById(experienceCommentId)
            .map(dto -> ExperienceCommentResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceCommentResponse> search(final String searchString) {

    return experienceCommentRepository
            .search(searchString)
            .stream()
            .map(dto -> ExperienceCommentResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
