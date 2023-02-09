package com.vire.repository;

import com.vire.dao.AppRestrictionDao;
import com.vire.dto.AppRestrictionDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppRestrictionRepository {

  @Autowired
  AppRestrictionRepositoryJpa appRestrictionRepositoryJpa;

  public AppRestrictionDto create(final AppRestrictionDto appRestrictionDto) {

    var appRestrictionDao = AppRestrictionDao.fromDto(appRestrictionDto);
    appRestrictionDao.onPrePersist();

    return appRestrictionRepositoryJpa.save(appRestrictionDao).toDto();
  }

  public AppRestrictionDto update(final AppRestrictionDto appRestrictionDto) {

    var existingObject = appRestrictionRepositoryJpa.findById(appRestrictionDto.getAppRestrictionId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var appRestrictionDao = AppRestrictionDao.fromDto(appRestrictionDto);
    appRestrictionDao.onPreUpdate();

    return appRestrictionRepositoryJpa.save(appRestrictionDao).toDto();
  }

  public Optional<AppRestrictionDto> delete(final Long appRestrictionId) {

    var optionalSocial = retrieveById(appRestrictionId);

    if (optionalSocial.isPresent()) {
      appRestrictionRepositoryJpa.deleteById(appRestrictionId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<AppRestrictionDto> getAll() {

    return appRestrictionRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<AppRestrictionDto> retrieveById(Long appRestrictionId) {

    return appRestrictionRepositoryJpa.findById(appRestrictionId).map(dao -> dao.toDto());
  }

  public List<AppRestrictionDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<AppRestrictionDao>(searchString).resolve();

    return appRestrictionRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
