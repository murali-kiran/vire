package com.vire.repository;

import com.vire.dao.ProfileNotificationDao;
import com.vire.dto.ProfileNotificationDto;
import com.vire.repository.ProfileNotificationRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileNotificationRepository {

  @Autowired
  ProfileNotificationRepositoryJpa profileNotificationRepositoryJpa;

  public ProfileNotificationDto create(final ProfileNotificationDto profileNotificationDto) {

    var profileNotificationDao = ProfileNotificationDao.fromDto(profileNotificationDto);
    profileNotificationDao.onPrePersist();

    return profileNotificationRepositoryJpa.save(profileNotificationDao).toDto();
  }

  public ProfileNotificationDto update(final ProfileNotificationDto profileNotificationDto) {

    var existingObject = profileNotificationRepositoryJpa.findById(profileNotificationDto.getProfileNotificationId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var profileNotificationDao = ProfileNotificationDao.fromDto(profileNotificationDto);
    profileNotificationDao.onPreUpdate();

    return profileNotificationRepositoryJpa.save(profileNotificationDao).toDto();
  }

  public Optional<ProfileNotificationDto> delete(final Long profileNotificationId) {

    var optionalSocial = retrieveById(profileNotificationId);

    if (optionalSocial.isPresent()) {
      profileNotificationRepositoryJpa.deleteById(profileNotificationId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ProfileNotificationDto> getAll() {

    return profileNotificationRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ProfileNotificationDto> retrieveById(Long profileNotificationId) {

    return profileNotificationRepositoryJpa.findById(profileNotificationId).map(dao -> dao.toDto());
  }

  public List<ProfileNotificationDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ProfileNotificationDao>(searchString).resolve();

    return profileNotificationRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
