package com.vire.service;

import com.vire.model.request.CommunityProfileRequest;
import com.vire.model.response.CommunityProfileResponse;
import com.vire.repository.CommunityProfileRepository;
import com.vire.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CommunityProfileService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  CommunityProfileRepository communityProfileRepository;
  @Autowired
  ProfileService profileService;

  public CommunityProfileResponse create(final CommunityProfileRequest request) {
    log.info("Community id###############:"+request.getCommunityId()+"##Profile id ############:"+request.getProfileId()+"######status######"+request.getStatus());
    checkAdminStatusCount(request);
    var existingCommunityProfile = communityProfileRepository.retrieveByCommunityIdAndProfileId(Long.valueOf(request.getCommunityId()), Long.valueOf(request.getProfileId()));
    if(existingCommunityProfile.isEmpty()) {
      var dto = request.toDto(snowflake);
      return CommunityProfileResponse.fromDto(communityProfileRepository.create(dto));
    }else{
      throw new RuntimeException("Record already exists please update");
    }
  }

  public CommunityProfileResponse update(final CommunityProfileRequest request) {
    log.info("Community id###############:"+request.getCommunityId()+"##Profile id ############:"+request.getProfileId()+"######status######"+request.getStatus());
    checkAdminStatusCount(request);
    var dto = request.toDto();
    return CommunityProfileResponse.fromDto(communityProfileRepository.update(dto));
  }
  public CommunityProfileResponse updateCommunityProfileStatus(final CommunityProfileRequest request) {
    log.info("Community id###############:"+request.getCommunityId()+"##Profile id ############:"+request.getProfileId()+"######status######"+request.getStatus());
    checkAdminStatusCount(request);
    var communityProfile = communityProfileRepository
            .retrieveByCommunityIdAndProfileId(Long.valueOf(request.getCommunityId()), Long.valueOf(request.getProfileId()));
    if(communityProfile != null && !communityProfile.isEmpty()) {
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

  public List<CommunityProfileResponse> deleteByCommunityID(final Long communityId) {
    return communityProfileRepository.deleteByCommunityID(communityId).stream().map(dto -> CommunityProfileResponse.fromDto(dto)).collect(Collectors.toList());
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

    private String checkAdminStatusCount(CommunityProfileRequest request){
      var communityProfiles = communityProfileRepository.search("( communityId:"+request.getCommunityId()+" ) AND ( status:Admin )");
      if(communityProfiles.size() == 3) {
        if(request.getStatus().equals("Admin")){
          throw new RuntimeException("max allowed admins reached");
        }
        return "max_admin_count";
      }
      else if(communityProfiles.size() == 1) {
        if(request.getStatus().equals("Exit")){
          throw new RuntimeException("Please make another person admin before exit");
        }
        return "one_admin";
      }
      else
        return "add_admin";
    }
}
