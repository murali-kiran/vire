package com.vire.service;

import com.vire.dao.FeedsDao;
import com.vire.dto.*;
import com.vire.model.request.*;
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

    return NotificationResponse.fromDto(notificationRepository.updateDeletedTime(notificationId));
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

  public void createProfileNotification(NotificationType notificationType, String responderProfileId,ProfileNotificationType profileNotificationType, String notifierProfileId){

    var notificationRequest = new NotificationRequest();
    notificationRequest.setNotificationType(notificationType);
    notificationRequest.setIsRead(false);
    var profileNotification = new ProfileNotificationRequest();
    profileNotification.setProfileNotificationType(profileNotificationType);
    profileNotification.setProfileId(responderProfileId);
    notificationRequest.setNotifierProfileId(notifierProfileId+"");
    notificationRequest.setMessage(profileNotificationType.name());
    notificationRequest.setProfileNotification(profileNotification);
    create(notificationRequest);
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
  public void createSocialNotification(NotificationType notificationType, Long requesterProfileId, SocialNotificationType socialNotificationType, Long socialId, String miscType, String socialMiscInfoId, Boolean isSocialCreatorChat){

    var notificationRequest = new NotificationRequest();
    notificationRequest.setNotificationType(notificationType);
    notificationRequest.setIsRead(false);
    var socialNotification = new SocialNotificationRequest();
    socialNotification.setSocialNotificationType(socialNotificationType);
    socialNotification.setSocialId(socialId);
    var social = socialRepositoryJpa.findById(socialId);
    socialNotification.setSocialMiscInfoId(socialMiscInfoId != null ? Long.valueOf(socialMiscInfoId) : null);
    socialNotification.setMiscType(miscType);
    if(socialNotificationType.name().contains("CALL_ACCEPTED") || socialNotificationType.name().contains("CALL_REJECTED")
      || (socialNotificationType.name().contains("CHAT") && isSocialCreatorChat)
    ) {
      notificationRequest.setNotifierProfileId(requesterProfileId + "");
      socialNotification.setProfileId(social.get().getProfileId());
    }else{
      notificationRequest.setNotifierProfileId(social.get().getProfileId()+"");
      socialNotification.setProfileId(requesterProfileId);
    }

    notificationRequest.setMessage(social.get().getDescription() == null ? "\"photo\"" : Utility.subStringOfSentence(social.get().getDescription(), 5));
    notificationRequest.setSocialNotification(socialNotification);
    create(notificationRequest);
  }

  public void createCommunityNotification(NotificationType notificationType, Long responderProfileId, CommunityNotificationType communityNotificationType, Long communityId){

    var notificationRequest = new NotificationRequest();
    var community = communityRepositoryJpa.findById(communityId);

    notificationRequest.setNotificationType(notificationType);
    notificationRequest.setIsRead(false);
    var communityNotification = new CommunityNotificationRequest();
    communityNotification.setCommunityNotificationType(communityNotificationType);
    communityNotification.setProfileId(communityNotificationType.name().equals("JOIN_REQUEST") ? responderProfileId : community.get().getCreatorProfileId());
    communityNotification.setCommunityId(communityId);
    notificationRequest.setNotifierProfileId(communityNotificationType.name().equals("JOIN_REQUEST") ? community.get().getCreatorProfileId()+"": responderProfileId+"");
    //notificationRequest.setMessage(community.get().getDescription() == null ? "\"photo\"" : Utility.subStringOfSentence(community.get().getDescription(), 5));
    notificationRequest.setMessage(community.get().getName());
    notificationRequest.setCommunityNotification(communityNotification);
    create(notificationRequest);
  }

  public List<NotificationResponse> retrieveNotificationsByTypeProfile(NotificationType notificationType, String profileId){

    List<NotificationResponse> notificationResponseList;
    if(notificationType == NotificationType.APP){
      notificationResponseList = notificationRepository
              .retrieveNotificationsByTypeAndProfile(
                      List.of(NotificationType.SOCIAL, NotificationType.FEED, NotificationType.PROFILE),
                      Long.valueOf(profileId))
              .stream()
              .map(dto -> NotificationResponse.fromDto(dto))
              .collect(Collectors.toList());

      }else {
        notificationResponseList = notificationRepository.retrieveNotificationsByTypeAndProfile(List.of(notificationType), Long.valueOf(profileId)).stream()
              .map(dto -> NotificationResponse.fromDto(dto))
              .collect(Collectors.toList());
    }
    Long responderProfileId = null;
    for (NotificationResponse notificationResponse : notificationResponseList) {
      if(notificationResponse.getFeedNotification() != null) {
        responderProfileId = Long.valueOf(notificationResponse.getFeedNotification().getProfileId());
      }else if(notificationResponse.getSocialNotification() != null) {
        responderProfileId = Long.valueOf(notificationResponse.getSocialNotification().getProfileId());
      }else if(notificationResponse.getCommunityNotification() != null){
        responderProfileId = Long.valueOf(notificationResponse.getCommunityNotification().getProfileId());
      }else if(notificationResponse.getProfileNotification() != null){
        responderProfileId = Long.valueOf(notificationResponse.getProfileNotification().getProfileId());
      }
      notificationResponse.setResponderProfile(profileService.retrieveProfileDtoById(responderProfileId));
    }
    return notificationResponseList;

  }
  public NotificationCountResponse retrieveCountByProfileId(Long profileId){
    NotificationCountResponse notificationCountResponse = new NotificationCountResponse();
    // OR ( notificationType:PROFILE )
    var appCount = notificationRepository
            .countByNotificationTypeAndNotifierProfileIdAndIsRead(NotificationType.SOCIAL, profileId, false) +
            notificationRepository
                    .countByNotificationTypeAndNotifierProfileIdAndIsRead(NotificationType.FEED, profileId, false)+
            notificationRepository
                    .countByNotificationTypeAndNotifierProfileIdAndIsRead(NotificationType.PROFILE, profileId, false);

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
