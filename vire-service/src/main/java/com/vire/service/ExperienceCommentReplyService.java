package com.vire.service;

import com.vire.model.request.ExperienceCommentReplyRequest;
import com.vire.model.response.ExperienceCommentReplyResponse;
import com.vire.repository.ExperienceCommentReplyRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceCommentReplyService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ExperienceCommentReplyRepository experienceCommentReplyRepository;

  public ExperienceCommentReplyResponse create(final ExperienceCommentReplyRequest request) {

    var dto = request.toDto(snowflake);

    return ExperienceCommentReplyResponse.fromDto(experienceCommentReplyRepository.create(dto));
  }

  public ExperienceCommentReplyResponse update(final ExperienceCommentReplyRequest request) {

    var dto = request.toDto();

    return ExperienceCommentReplyResponse.fromDto(experienceCommentReplyRepository.update(dto));
  }

  public ExperienceCommentReplyResponse delete(final Long experienceCommentReplyId) {

    return experienceCommentReplyRepository.delete(experienceCommentReplyId)
            .map(dto -> ExperienceCommentReplyResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceCommentReplyResponse> getAll() {

    return experienceCommentReplyRepository
            .getAll()
            .stream()
            .map(dto -> ExperienceCommentReplyResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ExperienceCommentReplyResponse retrieveById(Long experienceCommentReplyId) {

    return experienceCommentReplyRepository
            .retrieveById(experienceCommentReplyId)
            .map(dto -> ExperienceCommentReplyResponse.fromDto(dto))
            .get();
  }

  public List<ExperienceCommentReplyResponse> search(final String searchString) {

    return experienceCommentReplyRepository
            .search(searchString)
            .stream()
            .map(dto -> ExperienceCommentReplyResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
