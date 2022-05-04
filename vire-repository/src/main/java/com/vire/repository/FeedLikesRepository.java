package com.vire.repository;

import com.vire.dao.FeedLikesDao;
import com.vire.dto.FeedLikesDto;
import com.vire.repository.FeedLikesRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedLikesRepository {

  @Autowired
  FeedLikesRepositoryJpa feedLikesRepositoryJpa;

  public FeedLikesDto create(final FeedLikesDto feedLikesDto) {

    var feedLikesDao = FeedLikesDao.fromDto(feedLikesDto);
    feedLikesDao.onPrePersist();

    return feedLikesRepositoryJpa.save(feedLikesDao).toDto();
  }

  public FeedLikesDto update(final FeedLikesDto feedLikesDto) {

    var existingObject = feedLikesRepositoryJpa.findById(feedLikesDto.getFeedLikesId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var feedLikesDao = FeedLikesDao.fromDto(feedLikesDto);
    feedLikesDao.onPreUpdate();

    return feedLikesRepositoryJpa.save(feedLikesDao).toDto();
  }

  public Optional<FeedLikesDto> delete(final Long feedLikesId) {

    var optionalSocial = retrieveById(feedLikesId);

    if (optionalSocial.isPresent()) {
      feedLikesRepositoryJpa.deleteById(feedLikesId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<FeedLikesDto> getAll() {

    return feedLikesRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<FeedLikesDto> retrieveById(Long feedLikesId) {

    return feedLikesRepositoryJpa.findById(feedLikesId).map(dao -> dao.toDto());
  }

  public List<FeedLikesDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<FeedLikesDao>(searchString).resolve();

    return feedLikesRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
