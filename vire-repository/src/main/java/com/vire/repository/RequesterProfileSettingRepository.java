package com.vire.repository;

import com.vire.dao.RequesterProfileSettingDao;
import com.vire.dto.RequesterProfileSettingDto;
import com.vire.model.request.RequesterProfileSettingRequest;
import com.vire.repository.RequesterProfileSettingRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequesterProfileSettingRepository {

  @Autowired
  RequesterProfileSettingRepositoryJpa requesterProfileSettingRepositoryJpa;

  public RequesterProfileSettingDto create(final RequesterProfileSettingDto requesterProfileSettingDto) {

    var requesterProfileSettingDao = RequesterProfileSettingDao.fromDto(requesterProfileSettingDto);
    requesterProfileSettingDao.onPrePersist();

    return requesterProfileSettingRepositoryJpa.save(requesterProfileSettingDao).toDto();
  }

  public RequesterProfileSettingDto update(final RequesterProfileSettingDto requesterProfileSettingDto) {

    var existingObject = requesterProfileSettingRepositoryJpa.findById(requesterProfileSettingDto.getRequesterProfileSettingId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var requesterProfileSettingDao = RequesterProfileSettingDao.fromDto(requesterProfileSettingDto);
    requesterProfileSettingDao.onPreUpdate();

    return requesterProfileSettingRepositoryJpa.save(requesterProfileSettingDao).toDto();
  }
  public RequesterProfileSettingDto updateEnableStatus(RequesterProfileSettingRequest request) {

    var existingObject = requesterProfileSettingRepositoryJpa.findByProfileIdAndRequesterProfileIdAndSettingType
            (Long.valueOf(request.getProfileId()), Long.valueOf(request.getRequesterProfileId()), request.getSettingType());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }
    existingObject.get().setStatus(request.getStatus());
    existingObject.get().setIsEnabled(request.getIsEnabled());
    existingObject.get().onPreUpdate();
    return requesterProfileSettingRepositoryJpa.save(existingObject.get()).toDto();
  }
  public Optional<RequesterProfileSettingDto> delete(final Long requesterProfileSettingId) {

    var optionalSocial = retrieveById(requesterProfileSettingId);

    if (optionalSocial.isPresent()) {
      requesterProfileSettingRepositoryJpa.deleteById(requesterProfileSettingId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<RequesterProfileSettingDto> getAll() {

    return requesterProfileSettingRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<RequesterProfileSettingDto> retrieveById(Long requesterProfileSettingId) {

    return requesterProfileSettingRepositoryJpa.findById(requesterProfileSettingId).map(dao -> dao.toDto());
  }

  public List<RequesterProfileSettingDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<RequesterProfileSettingDao>(searchString).resolve();

    return requesterProfileSettingRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
