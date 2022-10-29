package com.vire.repository;

import com.vire.dao.NotificationDao;
import com.vire.dto.NotificationDto;

import com.vire.dto.NotificationType;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationRepository {

  @Autowired
  NotificationRepositoryJpa notificationRepositoryJpa;

  public NotificationDto create(final NotificationDto notificationDto) {

    var notificationDao = NotificationDao.fromDto(notificationDto);
    notificationDao.onPrePersist();

    return notificationRepositoryJpa.save(notificationDao).toDto();
  }

  public NotificationDto update(final NotificationDto notificationDto) {

    var existingObject = notificationRepositoryJpa.findById(notificationDto.getNotificationId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    /*var notificationDao = NotificationDao.fromDto(notificationDto);
    notificationDao.onPreUpdate();*/

    existingObject.get().setIsRead(true);
    return notificationRepositoryJpa.save(existingObject.get()).toDto();
  }

  public Optional<NotificationDto> delete(final Long notificationId) {

    var optionalSocial = retrieveById(notificationId);

    if (optionalSocial.isPresent()) {
      notificationRepositoryJpa.deleteById(notificationId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<NotificationDto> getAll() {

    return notificationRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<NotificationDto> retrieveById(Long notificationId) {

    return notificationRepositoryJpa.findById(notificationId).map(dao -> dao.toDto());
  }

  public List<NotificationDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<NotificationDao>(searchString).resolve();

    return notificationRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

  public Long countByNotificationTypeAndNotifierProfileIdAndIsRead(NotificationType notificationType, Long notifierProfileId, Boolean isRead){
    return notificationRepositoryJpa.countByNotificationTypeAndNotifierProfileIdAndIsReadAndDeletedTimeIsNull(notificationType, notifierProfileId, isRead);
  }

  public List<NotificationDto> retrieveNotificationsByTypeAndProfile(NotificationType notificationType, Long notifierProfileId){
    return notificationRepositoryJpa
            .findByNotificationTypeAndNotifierProfileIdAndIsReadAndDeletedTimeIsNullOrderByUpdatedTimeDesc(notificationType,notifierProfileId,false).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
