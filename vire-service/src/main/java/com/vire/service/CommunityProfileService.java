package com.vire.service;

import com.vire.model.request.CommunityProfileRequest;
import com.vire.model.response.CommunityProfileResponse;
import com.vire.repository.CommunityProfileRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityProfileService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  CommunityProfileRepository communityProfileRepository;
  @Autowired
  ProfileService profileService;

  public CommunityProfileResponse create(final CommunityProfileRequest request) {

    var dto = request.toDto(snowflake);

    return CommunityProfileResponse.fromDto(communityProfileRepository.create(dto));
  }

  public CommunityProfileResponse update(final CommunityProfileRequest request) {

    var dto = request.toDto();

    return CommunityProfileResponse.fromDto(communityProfileRepository.update(dto));
  }
  public CommunityProfileResponse updateCommunityProfileStatus(final CommunityProfileRequest request) {
    var communityProfile = communityProfileRepository
            .retrieveByCommunityIdAndProfileId(Long.valueOf(request.getCommunityId()), Long.valueOf(request.getProfileId()));
    if(communityProfile != null) {
      communityProfile.get().setStatus(request.getStatus());
      return CommunityProfileResponse.fromDto(communityProfileRepository.update(communityProfile.get()));
    }else{
      throw new RuntimeException("No record found with given profile and community Ids.");
    }
  }

  public CommunityProfileResponse delete(final Long communityProfileId) {

    return communityProfileRepository.delete(communityProfileId)
            .map(dto -> CommunityProfileResponse.fromDto(dto))
            .get();
  }

  public List<CommunityProfileResponse> getAll() {

    return communityProfileRepository
            .getAll()
            .stream()
            .map(dto -> CommunityProfileResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public CommunityProfileResponse retrieveById(Long communityProfileId) {

    return communityProfileRepository
            .retrieveById(communityProfileId)
            .map(dto -> CommunityProfileResponse.fromDto(dto))
            .get();
  }

  public List<CommunityProfileResponse> retrieveByCommunityId(String communityId) {
    return this.search("communityId:"+communityId);
  }

  public List<CommunityProfileResponse> search(final String searchString) {

    return communityProfileRepository
            .search(searchString)
            .stream()
            .map(dto -> {
              var communityProfileResponse = CommunityProfileResponse.fromDto(dto);
              if(communityProfileResponse.getProfile() != null) {
                communityProfileResponse.setProfile((profileService.retrieveProfileDtoById(
                        Long.valueOf(communityProfileResponse.getProfile().getProfileId()))));
              }
              return communityProfileResponse;
            })
            .collect(Collectors.toList());
  }

    public List<CommunityProfileResponse> retrieveByProfileId(String profileId) {
      return this.search("( profileId:"+profileId+ " ) AND ( status:Accepted )");
    }
}
