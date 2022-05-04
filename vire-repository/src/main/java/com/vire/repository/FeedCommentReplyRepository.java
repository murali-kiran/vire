package com.vire.repository;

import com.vire.dao.FeedCommentReplyDao;
import com.vire.dto.FeedCommentReplyDto;
import com.vire.repository.FeedCommentReplyRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedCommentReplyRepository {

  @Autowired
  FeedCommentReplyRepositoryJpa feedCommentReplyRepositoryJpa;

  public FeedCommentReplyDto create(final FeedCommentReplyDto feedCommentReplyDto) {

    var feedCommentReplyDao = FeedCommentReplyDao.fromDto(feedCommentReplyDto);
    feedCommentReplyDao.onPrePersist();

    return feedCommentReplyRepositoryJpa.save(feedCommentReplyDao).toDto();
  }

  public FeedCommentReplyDto update(final FeedCommentReplyDto feedCommentReplyDto) {

    var existingObject = feedCommentReplyRepositoryJpa.findById(feedCommentReplyDto.getFeedCommentReplyId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var feedCommentReplyDao = FeedCommentReplyDao.fromDto(feedCommentReplyDto);
    feedCommentReplyDao.onPreUpdate();

    return feedCommentReplyRepositoryJpa.save(feedCommentReplyDao).toDto();
  }

  public Optional<FeedCommentReplyDto> delete(final Long feedCommentReplyId) {

    var optionalSocial = retrieveById(feedCommentReplyId);

    if (optionalSocial.isPresent()) {
      feedCommentReplyRepositoryJpa.deleteById(feedCommentReplyId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<FeedCommentReplyDto> getAll() {

    return feedCommentReplyRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<FeedCommentReplyDto> retrieveById(Long feedCommentReplyId) {

    return feedCommentReplyRepositoryJpa.findById(feedCommentReplyId).map(dao -> dao.toDto());
  }

  public List<FeedCommentReplyDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<FeedCommentReplyDao>(searchString).resolve();

    return feedCommentReplyRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
