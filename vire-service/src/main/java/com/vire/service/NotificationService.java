package com.vire.service;

import com.vire.dto.FeedNotificationType;
import com.vire.dto.NotificationType;
import com.vire.model.request.FeedNotificationRequest;
import com.vire.model.request.NotificationRequest;
import com.vire.model.response.ExperienceDetailResponse;
import com.vire.model.response.NotificationResponse;
import com.vire.repository.NotificationRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  NotificationRepository notificationRepository;
  @Autowired
  ProfileService profileService;

  public NotificationResponse create(final NotificationRequest request) {

    var dto = request.toDto(snowflake);

    return NotificationResponse.fromDto(notificationRepository.create(dto));
  }

  public NotificationResponse update(final NotificationRequest request) {

    var dto = request.toDto();

    return NotificationResponse.fromDto(notificationRepository.update(dto));
  }

  public NotificationResponse delete(final Long notificationId) {

    return notificationRepository.delete(notificationId)
            .map(dto -> NotificationResponse.fromDto(dto))
            .get();
  }

  public List<NotificationResponse> getAll() {

    return notificationRepository
            .getAll()
            .stream()
            .map(dto -> NotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public NotificationResponse retrieveById(Long notificationId) {

    return notificationRepository
            .retrieveById(notificationId)
            .map(dto -> NotificationResponse.fromDto(dto))
            .get();
  }

  public List<NotificationResponse> search(final String searchString) {

    List<NotificationResponse> notificationResponseList = notificationRepository
            .search(searchString)
            .stream()
            .map(dto -> NotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
    for (NotificationResponse notificationResponse : notificationResponseList) {
      if(notificationResponse.getFeedNotification() != null) {
        notificationResponse.getFeedNotification().setResponderProfile(profileService.retrieveProfileDtoById(Long.valueOf(notificationResponse.getFeedNotification().getProfileId())));
      }
    }
    return notificationResponseList;
  }

  public void createFeedNotification(NotificationType notificationType, String notifierProfileId, Long responderProfileId,FeedNotificationType feedNotificationType, Long feedId){

    var notificationRequest = new NotificationRequest();
    notificationRequest.setNotificationType(notificationType);
    notificationRequest.setNotifierProfileId(notifierProfileId);
    notificationRequest.setIsRead(false);
    var feedNotification = new FeedNotificationRequest();
    feedNotification.setFeedNotificationType(feedNotificationType);
    feedNotification.setProfileId(responderProfileId);
    feedNotification.setFeedId(feedId);
    notificationRequest.setFeedNotification(feedNotification);
    create(notificationRequest);
  }
}
