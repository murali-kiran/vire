package com.vire.service;

import com.vire.model.request.ProfileBlockRequest;
import com.vire.model.request.ProfileFollowersRequest;
import com.vire.model.response.CommentResponse;
import com.vire.model.response.ProfileBlockResponse;
import com.vire.repository.ProfileBlockRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileBlockService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ProfileBlockRepository profileBlockRepository;

  @Autowired
  ProfileService profileService;

  @Autowired
  ProfileFollowersService profileFollowersService;

  @Transactional
  public ProfileBlockResponse create(final ProfileBlockRequest request) {

    var profileFollowers = profileFollowersService.search("profileId:"+request.getProfileId()+" AND followerId:"+request.getBlockedProfileId()+" AND status:Accepted");
    if(!profileFollowers.isEmpty()) {
      updateFollowStatus(request.getProfileId(), request.getBlockedProfileId());
    }
    var profileFollowers1 = profileFollowersService.search("profileId:"+request.getBlockedProfileId()+" AND followerId:"+request.getProfileId()+" AND status:Accepted");
    if(!profileFollowers1.isEmpty()) {
      updateFollowStatus(request.getBlockedProfileId(), request.getProfileId());
    }
    var dto = request.toDto(snowflake);
    return ProfileBlockResponse.fromDto(profileBlockRepository.create(dto));
  }

  private void updateFollowStatus(String profileId, String blockedProfileId){
      ProfileFollowersRequest profileFollowersRequest = new ProfileFollowersRequest();
      profileFollowersRequest.setProfileId(profileId);
      profileFollowersRequest.setFollowerId(blockedProfileId);
      profileFollowersRequest.setStatus("blocked");
      profileFollowersService.updateFollowStatus(profileFollowersRequest);
  }

  public ProfileBlockResponse update(final ProfileBlockRequest request) {

    var dto = request.toDto();

    return ProfileBlockResponse.fromDto(profileBlockRepository.update(dto));
  }

  public ProfileBlockResponse delete(final Long profileBlockId) {

    return profileBlockRepository.delete(profileBlockId)
            .map(dto -> ProfileBlockResponse.fromDto(dto))
            .get();
  }

  public ProfileBlockResponse unblock(final Long profileId, final Long blockedProfileId) {

    List<ProfileBlockResponse> list = search("profileId:"+profileId+" AND blockedProfileId:"+blockedProfileId);
    if(!list.isEmpty()) {
      return profileBlockRepository.delete(Long.valueOf(list.get(0).getProfileBlockId()))
              .map(dto -> ProfileBlockResponse.fromDto(dto))
              .get();
    }
    throw new RuntimeException("Object not found in block list for given profile id :"+profileId+" blockedProfileId:"+blockedProfileId);
  }

  public List<ProfileBlockResponse> getAll() {

    return profileBlockRepository
            .getAll()
            .stream()
            .map(dto -> ProfileBlockResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ProfileBlockResponse retrieveById(Long profileBlockId) {

    return profileBlockRepository
            .retrieveById(profileBlockId)
            .map(dto -> ProfileBlockResponse.fromDto(dto))
            .get();
  }

  public List<ProfileBlockResponse> search(final String searchString) {

    return profileBlockRepository
            .search(searchString)
            .stream()
            .map(dto -> profileLoader(ProfileBlockResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }
  public List<ProfileBlockResponse> getBlockedListByProfile(final String profileId) {

    // List<ProfileBlockResponse> blockedList = search("profileId:"+profileId);
    return profileBlockRepository
            .retrieveBlockedProfilesByProfileId(profileId)
            .stream()
            .map(dto -> profileLoader(ProfileBlockResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }

  private ProfileBlockResponse profileLoader(ProfileBlockResponse response){
    if (response.getBlockerProfile() != null
            && response.getBlockerProfile().getProfileId() != null) {
      response.getBlockerProfile().cloneProperties(
              profileService.retrieveProfileDtoById(
                      Long.valueOf(response.getBlockerProfile().getProfileId())));
    }

    return response;
  }
}
