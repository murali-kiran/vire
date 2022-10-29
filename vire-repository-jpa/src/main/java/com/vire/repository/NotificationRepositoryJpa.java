package com.vire.repository;

import com.vire.dao.NotificationDao;
import com.vire.dto.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepositoryJpa
    extends JpaRepository<NotificationDao, Long>, JpaSpecificationExecutor<NotificationDao> {

    Long countByNotificationTypeAndNotifierProfileIdAndIsReadAndDeletedTimeIsNull(NotificationType notificationType, Long notifierProfileId, Boolean isRead);

    List<NotificationDao> findByNotificationTypeAndNotifierProfileIdAndIsReadAndDeletedTimeIsNullOrderByUpdatedTimeDesc(NotificationType notificationType, Long notifierProfileId, Boolean isRead);
}
