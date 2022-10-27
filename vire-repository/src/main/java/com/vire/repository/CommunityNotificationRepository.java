package com.vire.repository;

import com.vire.dao.CommunityNotificationDao;
import com.vire.dto.CommunityNotificationDto;
import com.vire.repository.CommunityNotificationRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityNotificationRepository {

  @Autowired
  CommunityNotificationRepositoryJpa communityNotificationRepositoryJpa;

  public CommunityNotificationDto create(final CommunityNotificationDto communityNotificationDto) {

    var communityNotificationDao = CommunityNotificationDao.fromDto(communityNotificationDto);
    communityNotificationDao.onPrePersist();

    return communityNotificationRepositoryJpa.save(communityNotificationDao).toDto();
  }

  public CommunityNotificationDto update(final CommunityNotificationDto communityNotificationDto) {

    var existingObject = communityNotificationRepositoryJpa.findById(communityNotificationDto.getCommunityNotificationId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var communityNotificationDao = CommunityNotificationDao.fromDto(communityNotificationDto);
    communityNotificationDao.onPreUpdate();

    return communityNotificationRepositoryJpa.save(communityNotificationDao).toDto();
  }

  public Optional<CommunityNotificationDto> delete(final Long communityNotificationId) {

    var optionalSocial = retrieveById(communityNotificationId);

    if (optionalSocial.isPresent()) {
      communityNotificationRepositoryJpa.deleteById(communityNotificationId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<CommunityNotificationDto> getAll() {

    return communityNotificationRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<CommunityNotificationDto> retrieveById(Long communityNotificationId) {

    return communityNotificationRepositoryJpa.findById(communityNotificationId).map(dao -> dao.toDto());
  }

  public List<CommunityNotificationDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<CommunityNotificationDao>(searchString).resolve();

    return communityNotificationRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
