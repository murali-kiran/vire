package com.vire.repository;

import com.vire.dao.FeedbackDao;
import com.vire.dto.FeedbackDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackRepository {

  @Autowired
  FeedbackRepositoryJpa feedbackRepositoryJpa;

  public FeedbackDto create(final FeedbackDto feedbackDto) {

    var feedbackDao = FeedbackDao.fromDto(feedbackDto);
    feedbackDao.onPrePersist();

    return feedbackRepositoryJpa.save(feedbackDao).toDto();
  }

  public FeedbackDto update(final FeedbackDto feedbackDto) {

    var existingObject = feedbackRepositoryJpa.findById(feedbackDto.getFeedbackId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var feedbackDao = FeedbackDao.fromDto(feedbackDto);
    feedbackDao.onPreUpdate();

    return feedbackRepositoryJpa.save(feedbackDao).toDto();
  }

  public Optional<FeedbackDto> delete(final Long feedbackId) {

    var optionalSocial = retrieveById(feedbackId);

    if (optionalSocial.isPresent()) {
      feedbackRepositoryJpa.deleteById(feedbackId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<FeedbackDto> getAll() {

    return feedbackRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<FeedbackDto> retrieveById(Long feedbackId) {

    return feedbackRepositoryJpa.findById(feedbackId).map(dao -> dao.toDto());
  }

  public List<FeedbackDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<FeedbackDao>(searchString).resolve();

    return feedbackRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
