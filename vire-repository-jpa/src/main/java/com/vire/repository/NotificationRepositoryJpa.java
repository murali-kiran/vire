package com.vire.repository;

import com.vire.dao.NotificationDao;
import com.vire.dto.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface NotificationRepositoryJpa
    extends JpaRepository<NotificationDao, Long>, JpaSpecificationExecutor<NotificationDao> {

    Long countByNotificationTypeAndNotifierProfileIdAndIsReadAndDeletedTimeIsNull(NotificationType notificationType, Long notifierProfileId, Boolean isRead);

    List<NotificationDao> findByNotificationTypeInAndNotifierProfileIdAndIsReadAndDeletedTimeIsNullOrderByUpdatedTimeDesc(List<NotificationType> notificationType, Long notifierProfileId, Boolean isRead);
    List<NotificationDao> findByNotificationTypeInAndNotifierProfileIdAndDeletedTimeIsNullOrderByUpdatedTimeDesc(List<NotificationType> notificationType, Long notifierProfileId);
    @Query(value="SELECT s.social_notification_id FROM social_notification s WHERE  s.social_id = :socialId ", nativeQuery = true)
    Set<Long> findBySocial(Long socialId);
    @Query(value="SELECT f.feed_notification_id FROM feed_notification f WHERE  f.feed_id = :feedId ", nativeQuery = true)
    Set<Long> findByFeed(Long feedId);
}
