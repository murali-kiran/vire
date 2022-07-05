package com.vire.repository;

import com.vire.dao.FeedCommentDao;
import com.vire.dto.FeedCommentDto;
import com.vire.repository.FeedCommentRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedCommentRepository {

  @Autowired
  FeedCommentRepositoryJpa feedCommentRepositoryJpa;

  public FeedCommentDto create(final FeedCommentDto feedCommentDto) {

    var feedCommentDao = FeedCommentDao.fromDto(feedCommentDto);
    feedCommentDao.onPrePersist();

    return feedCommentRepositoryJpa.save(feedCommentDao).toDto();
  }

  public FeedCommentDto update(final FeedCommentDto feedCommentDto) {

    var existingObject = feedCommentRepositoryJpa.findById(feedCommentDto.getFeedCommentId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var feedCommentDao = FeedCommentDao.fromDto(feedCommentDto);
    feedCommentDao.onPreUpdate();

    return feedCommentRepositoryJpa.save(feedCommentDao).toDto();
  }

  public Optional<FeedCommentDto> delete(final Long feedCommentId) {

    var optionalSocial = retrieveById(feedCommentId);

    if (optionalSocial.isPresent()) {
      feedCommentRepositoryJpa.deleteById(feedCommentId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<FeedCommentDto> getAll() {

    return feedCommentRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<FeedCommentDto> retrieveById(Long feedCommentId) {

    return feedCommentRepositoryJpa.findById(feedCommentId).map(dao -> dao.toDto());
  }

  public List<FeedCommentDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<FeedCommentDao>(searchString).resolve();

    return feedCommentRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
