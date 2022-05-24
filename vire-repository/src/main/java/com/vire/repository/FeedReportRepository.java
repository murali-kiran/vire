package com.vire.repository;

import com.vire.dao.FeedReportDao;
import com.vire.dto.FeedReportDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedReportRepository {

  @Autowired
  FeedReportRepositoryJpa feedReportRepositoryJpa;

  public FeedReportDto create(final FeedReportDto feedReportDto) {

    var feedReportDao = FeedReportDao.fromDto(feedReportDto);
    feedReportDao.onPrePersist();

    return feedReportRepositoryJpa.save(feedReportDao).toDto();
  }

  public FeedReportDto update(final FeedReportDto feedReportDto) {

    var existingObject = feedReportRepositoryJpa.findById(feedReportDto.getFeedReportId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var feedReportDao = FeedReportDao.fromDto(feedReportDto);
    feedReportDao.onPreUpdate();

    return feedReportRepositoryJpa.save(feedReportDao).toDto();
  }

  public Optional<FeedReportDto> delete(final Long feedReportId) {

    var optionalSocial = retrieveById(feedReportId);

    if (optionalSocial.isPresent()) {
      feedReportRepositoryJpa.deleteById(feedReportId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<FeedReportDto> getAll() {

    return feedReportRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<FeedReportDto> retrieveById(Long feedReportId) {

    return feedReportRepositoryJpa.findById(feedReportId).map(dao -> dao.toDto());
  }

  public List<FeedReportDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<FeedReportDao>(searchString).resolve();

    return feedReportRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
