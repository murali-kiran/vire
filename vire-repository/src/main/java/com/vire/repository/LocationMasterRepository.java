package com.vire.repository;

import com.vire.dao.LocationMasterDao;
import com.vire.dto.LocationMasterDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationMasterRepository {

  @Autowired
  LocationMasterRepositoryJpa locationMasterRepositoryJpa;

  public LocationMasterDto create(final LocationMasterDto locationMasterDto) {

    var locationMasterDao = LocationMasterDao.fromDto(locationMasterDto);
    locationMasterDao.onPrePersist();

    return locationMasterRepositoryJpa.save(locationMasterDao).toDto();
  }

  public LocationMasterDto update(final LocationMasterDto locationMasterDto) {

    var existingObject = locationMasterRepositoryJpa.findById(locationMasterDto.getLocationMasterId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var locationMasterDao = LocationMasterDao.fromDto(locationMasterDto);
    locationMasterDao.onPreUpdate();

    return locationMasterRepositoryJpa.save(locationMasterDao).toDto();
  }

  public Optional<LocationMasterDto> delete(final Long locationMasterId) {

    var optionalSocial = retrieveById(locationMasterId);

    if (optionalSocial.isPresent()) {
      locationMasterRepositoryJpa.deleteById(locationMasterId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<LocationMasterDto> getAll() {

    return locationMasterRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<LocationMasterDto> retrieveById(Long locationMasterId) {

    return locationMasterRepositoryJpa.findById(locationMasterId).map(dao -> dao.toDto());
  }

  public List<LocationMasterDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<LocationMasterDao>(searchString).resolve();

    return locationMasterRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
