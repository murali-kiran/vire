package com.vire.service;

import com.vire.dto.NotificationType;
import com.vire.dto.ProfileNotificationType;
import com.vire.model.request.ProfileThumbsUpRequest;
import com.vire.model.response.CommentResponse;
import com.vire.model.response.ProfileThumbsUpResponse;
import com.vire.repository.ProfileThumbsUpRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileThumbsUpService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ProfileThumbsUpRepository profileThumbsUpRepository;
  @Autowired
  ProfileService profileService;
  @Autowired
  NotificationService notificationService;
  public ProfileThumbsUpResponse create(final ProfileThumbsUpRequest request) {

    var dto = request.toDto(snowflake);
    try {
      notificationService.createProfileNotification(NotificationType.PROFILE, request.getThumbsUpBy(), ProfileNotificationType.THUMBS_UP, request.getProfileId());
      return ProfileThumbsUpResponse.fromDto(profileThumbsUpRepository.create(dto));
    }catch(Exception e){
      throw new RuntimeException("Thums up record not created for profile:"+request.getProfileId()+" by "+request.getThumbsUpBy());
    }

  }

  public ProfileThumbsUpResponse update(final ProfileThumbsUpRequest request) {

    var dto = request.toDto();

    return ProfileThumbsUpResponse.fromDto(profileThumbsUpRepository.update(dto));
  }

  public ProfileThumbsUpResponse delete(final Long profileThumbsUpId) {

    return profileThumbsUpRepository.delete(profileThumbsUpId)
            .map(dto -> ProfileThumbsUpResponse.fromDto(dto))
            .get();
  }

  public List<ProfileThumbsUpResponse> getAll() {

    return profileThumbsUpRepository
            .getAll()
            .stream()
            .map(dto -> ProfileThumbsUpResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ProfileThumbsUpResponse retrieveById(Long profileThumbsUpId) {

    return profileThumbsUpRepository
            .retrieveById(profileThumbsUpId)
            .map(dto -> ProfileThumbsUpResponse.fromDto(dto))
            .get();
  }

  public List<ProfileThumbsUpResponse> search(final String searchString) {

    return profileThumbsUpRepository
            .search(searchString)
            .stream()
            .map(dto -> profileLoader(ProfileThumbsUpResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }

  public long getThumbsUpCountOfProfile(Long profileId){
    return profileThumbsUpRepository.getThumbsUpCountOfProfile(profileId);
  }
  private ProfileThumbsUpResponse profileLoader(ProfileThumbsUpResponse response) {
    if (response.getThumbsUpProfile() != null
            && response.getThumbsUpProfile().getProfileId() != null) {
      response.getThumbsUpProfile().cloneProperties(
              profileService.retrieveProfileDtoById(Long.valueOf(response.getThumbsUpProfile().getProfileId())));
    }
    return response;
  }
}
