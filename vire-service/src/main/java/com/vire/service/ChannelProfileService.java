package com.vire.service;

import com.vire.model.request.ChannelProfileRequest;
import com.vire.model.request.CommunityProfileRequest;
import com.vire.model.response.ChannelProfileResponse;
import com.vire.model.response.CommunityProfileResponse;
import com.vire.repository.ChannelProfileRepository;
import com.vire.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChannelProfileService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ChannelProfileRepository channelProfileRepository;
  @Autowired
  ProfileService profileService;


  @Transactional
  public ChannelProfileResponse create(final ChannelProfileRequest request) {
    log.info("Community id###############:"+request.getChannelId()+"##Profile id ############:"+request.getProfileId()+"######status######"+request.getStatus());
    checkAdminStatusCount(request);
    var dto = request.toDto(snowflake);
    return ChannelProfileResponse.fromDto(channelProfileRepository.create(dto));
  }

  public ChannelProfileResponse update(final ChannelProfileRequest request) {
    log.info("Community id###############:"+request.getChannelId()+"##Profile id ############:"+request.getProfileId()+"######status######"+request.getStatus());
    checkAdminStatusCount(request);
    var dto = request.toDto();
    return ChannelProfileResponse.fromDto(channelProfileRepository.update(dto));
  }
  public ChannelProfileResponse updateChannelProfileStatus(final ChannelProfileRequest request) {
    log.info("Community id###############:"+request.getChannelId()+"##Profile id ############:"+request.getProfileId()+"######status######"+request.getStatus());
    checkAdminStatusCount(request);
    var channelProfile = channelProfileRepository.retrieveByChannelIdAndProfileId(Long.valueOf(request.getChannelId()), Long.valueOf(request.getProfileId()));
    if(channelProfile != null) {
      channelProfile.get().setStatus(request.getStatus());
      return ChannelProfileResponse.fromDto(channelProfileRepository.update(channelProfile.get()));
    }else{
      throw new RuntimeException("No record found with given profile and channel Ids.");
    }
  }

  public ChannelProfileResponse delete(final Long channelProfileId) {

    return channelProfileRepository.delete(channelProfileId)
            .map(dto -> ChannelProfileResponse.fromDto(dto))
            .get();
  }

  public List<ChannelProfileResponse> getAll() {

    return channelProfileRepository
            .getAll()
            .stream()
            .map(dto -> ChannelProfileResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ChannelProfileResponse retrieveById(Long channelProfileId) {

    return channelProfileRepository
            .retrieveById(channelProfileId)
            .map(dto -> ChannelProfileResponse.fromDto(dto))
            .get();
  }

  public List<ChannelProfileResponse> search(final String searchString) {

    return channelProfileRepository
            .search(searchString)
            .stream()
            .map(dto -> {
              var channelProfileResponse = ChannelProfileResponse.fromDto(dto);
              if(channelProfileResponse.getProfile() != null) {
                channelProfileResponse.setProfile((profileService.retrieveProfileDtoById(
                        Long.valueOf(channelProfileResponse.getProfile().getProfileId()))));
              }
              return channelProfileResponse;
            })
            .collect(Collectors.toList());
  }

  public List<ChannelProfileResponse> retrieveByChannelId(String channelId) {
    return this.search("channelId:"+channelId);
  }
  public List<ChannelProfileResponse> retrieveByProfileId(String profileId) {
    return this.search("( profileId:"+profileId+ " ) AND ( status:Accepted )");
  }

  public List<ChannelProfileResponse> retrieveChannelsCreatedJoined(String profileId) {
    return this.search("( profileId:"+profileId+ " ) AND ( ( status:Accepted ) OR ( status:Admin ) )");
  }
  private void checkAdminStatusCount(ChannelProfileRequest request){
    var channelProfiles = channelProfileRepository
            .search("( channelId:"+request.getChannelId()+" ) AND ( status:Admin )");
    if(channelProfiles.size() == 3 && request.getStatus().equals("Admin"))
       throw new RuntimeException("max allowed admins reached");
    else if(channelProfiles.size() == 1 && request.getStatus().equals("Exit") &&
            channelProfiles.get(0).getProfileId().toString().equals(request.getProfileId()))
      throw new RuntimeException("Make someone as channel admin before you leave current channel");
  }
}
