package com.vire.service;

import com.vire.model.request.ProfileFollowersRequest;
import com.vire.model.response.ProfileFollowersResponse;
import com.vire.model.response.ProfileThumbsDownResponse;
import com.vire.repository.ProfileFollowersRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileFollowersService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ProfileFollowersRepository profileFollowersRepository;

  @Autowired
  ProfileService profileService;

  public ProfileFollowersResponse create(final ProfileFollowersRequest request) {

    var dto = request.toDto(snowflake);

    return ProfileFollowersResponse.fromDto(profileFollowersRepository.create(dto));
  }

  public ProfileFollowersResponse update(final ProfileFollowersRequest request) {

    var dto = request.toDto();

    return ProfileFollowersResponse.fromDto(profileFollowersRepository.update(dto));
  }

  public ProfileFollowersResponse delete(final Long profileFollowersId) {

    return profileFollowersRepository.delete(profileFollowersId)
            .map(dto -> ProfileFollowersResponse.fromDto(dto))
            .get();
  }

  public List<ProfileFollowersResponse> getAll() {

    return profileFollowersRepository
            .getAll()
            .stream()
            .map(dto -> ProfileFollowersResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ProfileFollowersResponse retrieveById(Long profileFollowersId) {

    return profileFollowersRepository
            .retrieveById(profileFollowersId)
            .map(dto -> ProfileFollowersResponse.fromDto(dto))
            .get();
  }

  public List<ProfileFollowersResponse> search(final String searchString) {

    return profileFollowersRepository
            .search(searchString)
            .stream()
            .map(dto -> profileLoader(ProfileFollowersResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }

    public long getFriendCountOfProfile(Long profileId) {
      return profileFollowersRepository.getFriendCountOfProfile(profileId,true);
    }
  private ProfileFollowersResponse profileLoader(ProfileFollowersResponse response) {
    if (response.getProfile() != null
            && response.getProfile().getProfileId() != null) {
      response.getProfile().cloneProperties(
              profileService.retrieveProfileDtoById(
                      Long.valueOf(response.getProfile().getProfileId())));
    }
    if (response.getFollower() != null
            && response.getFollower().getProfileId() != null) {
      response.getFollower().cloneProperties(
              profileService.retrieveProfileDtoById(
                      Long.valueOf(response.getFollower().getProfileId())));
    }
    return response;
  }
  /*public void getFriendsOfProfile(Long profileId) {
    return profileFollowersRepository.getFriendsOfProfile(profileId,true);
  }*/
}
