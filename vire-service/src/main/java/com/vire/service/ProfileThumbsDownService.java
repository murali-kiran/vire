package com.vire.service;

import com.vire.dto.NotificationType;
import com.vire.dto.ProfileNotificationType;
import com.vire.model.request.ProfileThumbsDownRequest;
import com.vire.model.response.ProfileThumbsDownResponse;
import com.vire.model.response.ProfileThumbsUpResponse;
import com.vire.repository.ProfileThumbsDownRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileThumbsDownService {

  @Autowired
  Snowflake snowflake;
  @Autowired
  ProfileThumbsDownRepository profileThumbsDownRepository;
  @Autowired
  ProfileService profileService;
  @Autowired
  NotificationService notificationService;
  public ProfileThumbsDownResponse create(final ProfileThumbsDownRequest request) {

    var dto = request.toDto(snowflake);
    try {
      notificationService.createProfileNotification(NotificationType.PROFILE, request.getThumbsDownBy(), ProfileNotificationType.THUMBS_DOWN, request.getProfileId());
      return ProfileThumbsDownResponse.fromDto(profileThumbsDownRepository.create(dto));
    }catch(Exception e){
      throw new RuntimeException("Thumbs down record not created for profile:"+request.getProfileId()+" by "+request.getThumbsDownBy());
    }
  }

  public ProfileThumbsDownResponse update(final ProfileThumbsDownRequest request) {

    var dto = request.toDto();

    return ProfileThumbsDownResponse.fromDto(profileThumbsDownRepository.update(dto));
  }

  public ProfileThumbsDownResponse delete(final Long profileThumbsDownId) {

    return profileThumbsDownRepository.delete(profileThumbsDownId)
            .map(dto -> ProfileThumbsDownResponse.fromDto(dto))
            .get();
  }

  public List<ProfileThumbsDownResponse> getAll() {

    return profileThumbsDownRepository
            .getAll()
            .stream()
            .map(dto -> ProfileThumbsDownResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ProfileThumbsDownResponse retrieveById(Long profileThumbsDownId) {

    return profileThumbsDownRepository
            .retrieveById(profileThumbsDownId)
            .map(dto -> ProfileThumbsDownResponse.fromDto(dto))
            .get();
  }

  public List<ProfileThumbsDownResponse> search(final String searchString) {

    return profileThumbsDownRepository
            .search(searchString)
            .stream()
            .map(dto -> profileLoader(ProfileThumbsDownResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }

  public long getThumbsDownCountOfProfile(Long profileId){
    return profileThumbsDownRepository.getThumbsDownCountOfProfile(profileId);
  }

  private ProfileThumbsDownResponse profileLoader(ProfileThumbsDownResponse response) {
    if (response.getThumbsDownProfile() != null
            && response.getThumbsDownProfile().getProfileId() != null) {
      response.getThumbsDownProfile().cloneProperties(
              profileService.retrieveProfileDtoById(
                      Long.valueOf(response.getThumbsDownProfile().getProfileId())));
    }
    return response;
  }
}
