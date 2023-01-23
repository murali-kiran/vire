package com.vire.service;

import com.vire.dto.CommunityNotificationType;
import com.vire.dto.CommunityProfileDto;
import com.vire.dto.NotificationType;
import com.vire.dto.SocialNotificationType;
import com.vire.model.request.CommunityProfileRequest;
import com.vire.model.response.CommunityProfileResponse;
import com.vire.repository.CommunityProfileRepository;
import com.vire.repository.CommunityRepository;
import com.vire.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Autowired
  NotificationService notificationService;
  @Autowired
  CommunityRepository communityRepository;

  public CommunityProfileResponse create(final CommunityProfileRequest request) {
    log.info("Community id###############:"+request.getCommunityId()+"##Profile id ############:"+request.getProfileId()+"######status######"+request.getStatus());
    checkAdminStatusCount(request);
    var existingCommunityProfile = communityProfileRepository.retrieveByCommunityIdAndProfileId(Long.valueOf(request.getCommunityId()), Long.valueOf(request.getProfileId()));
    if(existingCommunityProfile.isEmpty()) {
      var dto = request.toDto(snowflake);
      try {
        CommunityNotificationType communityNotificationType = null;
        if(request.getStatus().equalsIgnoreCase("requested")){
          communityNotificationType = CommunityNotificationType.JOIN_REQUEST;
        }else if(request.getStatus().equalsIgnoreCase("accepted")){
          communityNotificationType = CommunityNotificationType.USER_ADDED;
        }else if(request.getStatus().equalsIgnoreCase("admin")){
          communityNotificationType = CommunityNotificationType.ADMIN_ADDED;
        }
        if(request.getIsCreator() == null) {
          notificationService.createCommunityNotification(NotificationType.COMMUNITY, Long.valueOf(request.getProfileId()),
                  communityNotificationType, Long.valueOf(request.getCommunityId()));
        }
      }
      catch (Exception e){
        throw new RuntimeException("Community Notification failed to create for community id:"+request.getCommunityId()+" due to "+e.getMessage() );
      }
      if(request.getIsAdmin() == null){
        dto.setIsAdmin(false);
      }
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
  @Transactional
  public CommunityProfileResponse updateCommunityProfileStatus(final CommunityProfileRequest request) {
    log.info("Community id###############:"+request.getCommunityId()+"##Profile id ############:"+request.getProfileId()+"######status######"+request.getStatus());
    int adminCount = checkAdminStatusCount(request);
    var communityProfile = communityProfileRepository
            .retrieveByCommunityIdAndProfileId(Long.valueOf(request.getCommunityId()), Long.valueOf(request.getProfileId()));
    if(communityProfile != null && !communityProfile.isEmpty()) {
      communityProfile.get().setStatus(request.getStatus());
      if(request.getStatus().equals("Requested")){
        if (request.getCommunityFileList() != null && !request.getCommunityFileList().isEmpty()) {
          communityProfile.get().setCommunityFileList(request.getCommunityFileList()
                  .stream()
                  .map(child -> child.toDto(snowflake))
                  .collect(Collectors.toList()));
        }
      }
      if(request.getStatus().equalsIgnoreCase("exit") && communityProfile.get().getIsAdmin()){
        updateCommunityCreator(communityProfile.get());
        communityProfile.get().setIsAdmin(false);
      }
      CommunityNotificationType communityNotificationType = null;
      if(request.getStatus().equalsIgnoreCase("requested")){
        communityNotificationType = CommunityNotificationType.JOIN_REQUEST;
      }else if(request.getStatus().equalsIgnoreCase("accepted")){
        communityNotificationType = CommunityNotificationType.JOINED;
      }else if(request.getStatus().equalsIgnoreCase("admin")){
        communityNotificationType = CommunityNotificationType.ADMIN_ADDED;
      }
      if(communityNotificationType != null) {
        try {
          notificationService.createCommunityNotification(NotificationType.COMMUNITY, Long.valueOf(request.getProfileId()),
                  communityNotificationType, Long.valueOf(request.getCommunityId()));
        }catch (Exception e){
          throw new RuntimeException("Community Notification failed to create for community id:"+request.getCommunityId()+" due to "+e.getMessage() );
        }
      }
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
      return this.search("( profileId:"+profileId+ " ) AND ( ( status:Accepted ) OR ( status:Admin ) ) AND ( isAdmin:false )");
    }

  public List<CommunityProfileResponse> retrieveCommunitiesCreatedJoined(String profileId) {
    return this.search("( profileId:"+profileId+ " ) AND ( ( status:Accepted ) OR ( status:Admin ) )");
  }

  private int checkAdminStatusCount(CommunityProfileRequest request){
    var communityProfiles = communityProfileRepository
            .search("( communityId:"+request.getCommunityId()+" ) AND ( status:Admin )");
    if(communityProfiles.size() == 3 && request.getStatus().equals("Admin"))
      throw new RuntimeException("max allowed admins reached");
    else if(communityProfiles.size() == 1 && request.getStatus().equals("Exit") &&
            communityProfiles.get(0).getProfileId().toString().equals(request.getProfileId()))
      throw new RuntimeException("Make someone as community admin before you leave current community");
    return communityProfiles.size();
  }
  private void updateCommunityCreator(CommunityProfileDto request){
     var community = communityRepository.retrieveById(Long.valueOf(request.getCommunityId()));
       if(community.isPresent()){
         var newCommunityProfiles = communityProfileRepository
                 .search("( communityId:"+request.getCommunityId()+" ) AND ( status:Admin )");
         if(!newCommunityProfiles.isEmpty()) {
           community.get().setCreatorProfileId(newCommunityProfiles.get(1).getProfileId());
           communityRepository.update(community.get());
           newCommunityProfiles.get(1).setIsAdmin(true);
           communityProfileRepository.update(newCommunityProfiles.get(1));
         }
       }
     }
}
