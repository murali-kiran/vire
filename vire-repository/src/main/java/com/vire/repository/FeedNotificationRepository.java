package com.vire.repository;

import com.vire.dao.FeedNotificationDao;
import com.vire.dto.FeedNotificationDto;
import com.vire.repository.FeedNotificationRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedNotificationRepository {

  @Autowired
  FeedNotificationRepositoryJpa feedNotificationRepositoryJpa;

  public FeedNotificationDto create(final FeedNotificationDto feedNotificationDto) {

    var feedNotificationDao = FeedNotificationDao.fromDto(feedNotificationDto);
    feedNotificationDao.onPrePersist();

    return feedNotificationRepositoryJpa.save(feedNotificationDao).toDto();
  }

  public FeedNotificationDto update(final FeedNotificationDto feedNotificationDto) {

    var existingObject = feedNotificationRepositoryJpa.findById(feedNotificationDto.getFeedNotificationId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var feedNotificationDao = FeedNotificationDao.fromDto(feedNotificationDto);
    feedNotificationDao.onPreUpdate();

    return feedNotificationRepositoryJpa.save(feedNotificationDao).toDto();
  }

  public Optional<FeedNotificationDto> delete(final Long feedNotificationId) {

    var optionalSocial = retrieveById(feedNotificationId);

    if (optionalSocial.isPresent()) {
      feedNotificationRepositoryJpa.deleteById(feedNotificationId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<FeedNotificationDto> getAll() {

    return feedNotificationRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<FeedNotificationDto> retrieveById(Long feedNotificationId) {

    return feedNotificationRepositoryJpa.findById(feedNotificationId).map(dao -> dao.toDto());
  }

  public List<FeedNotificationDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<FeedNotificationDao>(searchString).resolve();

    return feedNotificationRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
