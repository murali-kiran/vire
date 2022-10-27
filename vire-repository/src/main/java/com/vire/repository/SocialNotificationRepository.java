package com.vire.repository;

import com.vire.dao.SocialNotificationDao;
import com.vire.dto.SocialNotificationDto;
import com.vire.repository.SocialNotificationRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialNotificationRepository {

  @Autowired
  SocialNotificationRepositoryJpa socialNotificationRepositoryJpa;

  public SocialNotificationDto create(final SocialNotificationDto socialNotificationDto) {

    var socialNotificationDao = SocialNotificationDao.fromDto(socialNotificationDto);
    socialNotificationDao.onPrePersist();

    return socialNotificationRepositoryJpa.save(socialNotificationDao).toDto();
  }

  public SocialNotificationDto update(final SocialNotificationDto socialNotificationDto) {

    var existingObject = socialNotificationRepositoryJpa.findById(socialNotificationDto.getSocialNotificationId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var socialNotificationDao = SocialNotificationDao.fromDto(socialNotificationDto);
    socialNotificationDao.onPreUpdate();

    return socialNotificationRepositoryJpa.save(socialNotificationDao).toDto();
  }

  public Optional<SocialNotificationDto> delete(final Long socialNotificationId) {

    var optionalSocial = retrieveById(socialNotificationId);

    if (optionalSocial.isPresent()) {
      socialNotificationRepositoryJpa.deleteById(socialNotificationId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<SocialNotificationDto> getAll() {

    return socialNotificationRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<SocialNotificationDto> retrieveById(Long socialNotificationId) {

    return socialNotificationRepositoryJpa.findById(socialNotificationId).map(dao -> dao.toDto());
  }

  public List<SocialNotificationDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<SocialNotificationDao>(searchString).resolve();

    return socialNotificationRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
