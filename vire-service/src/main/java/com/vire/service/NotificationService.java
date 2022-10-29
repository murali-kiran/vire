package com.vire.service;

import com.vire.dao.FeedsDao;
import com.vire.dto.*;
import com.vire.model.request.CommunityNotificationRequest;
import com.vire.model.request.FeedNotificationRequest;
import com.vire.model.request.NotificationRequest;
import com.vire.model.request.SocialNotificationRequest;
import com.vire.model.response.ExperienceDetailResponse;
import com.vire.model.response.FeedsResponse;
import com.vire.model.response.NotificationCountResponse;
import com.vire.model.response.NotificationResponse;
import com.vire.repository.CommunityRepositoryJpa;
import com.vire.repository.FeedsRepositoryJpa;
import com.vire.repository.NotificationRepository;
import com.vire.repository.SocialRepositoryJpa;
import com.vire.utils.Snowflake;
import com.vire.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  NotificationRepository notificationRepository;
  @Autowired
  ProfileService profileService;
  @Autowired
  FeedsRepositoryJpa feedsRepositoryJpa;
  @Autowired
  SocialRepositoryJpa socialRepositoryJpa;
  @Autowired
  CommunityRepositoryJpa communityRepositoryJpa;

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
    Long responderProfileId = null;
    List<NotificationResponse> notificationResponseList = notificationRepository
            .search(searchString)
            .stream()
            .map(dto -> NotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
    for (NotificationResponse notificationResponse : notificationResponseList) {
      if(notificationResponse.getFeedNotification() != null) {
        responderProfileId = Long.valueOf(notificationResponse.getFeedNotification().getProfileId());
      }else if(notificationResponse.getSocialNotification() != null) {
        responderProfileId = Long.valueOf(notificationResponse.getSocialNotification().getProfileId());
      }else if(notificationResponse.getCommunityNotification() != null) {
        responderProfileId = Long.valueOf(notificationResponse.getCommunityNotification().getProfileId());
      }
      notificationResponse.setResponderProfile(profileService.retrieveProfileDtoById(responderProfileId));
    }
    return notificationResponseList;
  }

  public void createFeedNotification(NotificationType notificationType, Long responderProfileId,FeedNotificationType feedNotificationType, Long feedId){

    var notificationRequest = new NotificationRequest();
    notificationRequest.setNotificationType(notificationType);
    notificationRequest.setIsRead(false);
    var feedNotification = new FeedNotificationRequest();
    feedNotification.setFeedNotificationType(feedNotificationType);
    feedNotification.setProfileId(responderProfileId);
    feedNotification.setFeedId(feedId);
    var feed = feedsRepositoryJpa.findById(feedId);
    notificationRequest.setNotifierProfileId(feed.get().getProfileId()+"");
    notificationRequest.setMessage(feed.get().getDescription() == null ? "\"photo\"" : Utility.subStringOfSentence(feed.get().getDescription(), 5));
    notificationRequest.setFeedNotification(feedNotification);
    create(notificationRequest);
  }
  public void createSocialNotification(NotificationType notificationType, Long responderProfileId, SocialNotificationType socialNotificationType, Long socialId){

    var notificationRequest = new NotificationRequest();
    notificationRequest.setNotificationType(notificationType);
    notificationRequest.setIsRead(false);
    var socialNotification = new SocialNotificationRequest();
    socialNotification.setSocialNotificationType(socialNotificationType);
    socialNotification.setProfileId(responderProfileId);
    socialNotification.setSocialId(socialId);
    var social = socialRepositoryJpa.findById(socialId);
    if(socialNotificationType.name().contains("CALL_ACCEPTED") || socialNotificationType.name().contains("REPLY_CHAT")) {
      notificationRequest.setNotifierProfileId(responderProfileId + "");
    }else{
      notificationRequest.setNotifierProfileId(social.get().getProfileId()+"");
    }
    notificationRequest.setMessage(social.get().getDescription() == null ? "\"photo\"" : Utility.subStringOfSentence(social.get().getDescription(), 5));
    notificationRequest.setSocialNotification(socialNotification);
    create(notificationRequest);
  }

  public void createCommunityNotification(NotificationType notificationType, Long responderProfileId, CommunityNotificationType communityNotificationType, Long communityId){

    var notificationRequest = new NotificationRequest();
    notificationRequest.setNotificationType(notificationType);
    notificationRequest.setIsRead(false);
    var communityNotification = new CommunityNotificationRequest();
    communityNotification.setCommunityNotificationType(communityNotificationType);
    communityNotification.setProfileId(responderProfileId);
    communityNotification.setCommunityId(communityId);
    var community = communityRepositoryJpa.findById(communityId);
    notificationRequest.setNotifierProfileId(communityNotificationType.name().equals("JOIN_REQUEST") ? community.get().getCreatorProfileId()+"": responderProfileId+"");
    notificationRequest.setMessage(community.get().getDescription() == null ? "\"photo\"" : Utility.subStringOfSentence(community.get().getDescription(), 5));
    notificationRequest.setCommunityNotification(communityNotification);
    create(notificationRequest);
  }

  public List<NotificationResponse> retrieveNotificationsByTypeProfile(String notificationType, String profileId){

    if(notificationType.equals("app_notifications")){
      List<NotificationResponse> notificationResponses = notificationRepository.retrieveNotificationsByTypeAndProfile(NotificationType.SOCIAL, Long.valueOf(profileId)).stream()
              .map(dto -> NotificationResponse.fromDto(dto))
              .collect(Collectors.toList());
      notificationResponses.addAll(notificationRepository.retrieveNotificationsByTypeAndProfile(NotificationType.FEED, Long.valueOf(profileId)).stream()
              .map(dto -> NotificationResponse.fromDto(dto))
              .collect(Collectors.toList()));
      notificationResponses.addAll(notificationRepository.retrieveNotificationsByTypeAndProfile(NotificationType.PROFILE, Long.valueOf(profileId)).stream()
              .map(dto -> NotificationResponse.fromDto(dto))
              .collect(Collectors.toList()));
      return notificationResponses;
    }else
      return notificationRepository.retrieveNotificationsByTypeAndProfile(NotificationType.valueOf(notificationType), Long.valueOf(profileId)).stream()
              .map(dto -> NotificationResponse.fromDto(dto))
              .collect(Collectors.toList());

  }
  public NotificationCountResponse retrieveCountByProfileId(Long profileId){
    NotificationCountResponse notificationCountResponse = new NotificationCountResponse();
    // OR ( notificationType:PROFILE )
    var appCount = notificationRepository
            .countByNotificationTypeAndNotifierProfileIdAndIsRead(NotificationType.SOCIAL, profileId, false) +
            notificationRepository
                    .countByNotificationTypeAndNotifierProfileIdAndIsRead(NotificationType.FEED, profileId, false);

    var chatCount = notificationRepository
            .countByNotificationTypeAndNotifierProfileIdAndIsRead(NotificationType.SOCIAL_CHAT, profileId, false);
    var communityCount = notificationRepository
            .countByNotificationTypeAndNotifierProfileIdAndIsRead(NotificationType.COMMUNITY, profileId, false);

    notificationCountResponse.setAppCount(appCount);
    notificationCountResponse.setSocialChatCount(chatCount);
    notificationCountResponse.setCommunityCount(communityCount);
    return notificationCountResponse;
  }
}
