package com.vire.service;

import com.vire.model.request.ChannelProfileRequest;
import com.vire.model.request.ChannelRequest;
import com.vire.model.response.ChannelResponse;
import com.vire.model.response.ChannelResponse;
import com.vire.repository.ChannelProfileRepository;
import com.vire.repository.ChannelRepository;
import com.vire.utils.Snowflake;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelService {

  @Autowired
  Snowflake snowflake;
  @Autowired
  ChannelProfileRepository channelProfileRepository;
  @Autowired
  ChannelRepository channelRepository;
  @Autowired
  ChannelProfileService channelProfileService;
  @Autowired
  ProfileService profileService;


  public ChannelResponse create(final ChannelRequest request) {
    try{
      var dto = request.toDto(snowflake);
      dto =  channelRepository.create(dto);

      var channelProfileRequest = new ChannelProfileRequest();
      channelProfileRequest.setChannelId(dto.getChannelId().toString());
      channelProfileRequest.setProfileId(dto.getCreatorProfileId().toString());
      channelProfileRequest.setStatus("Admin");
      channelProfileService.create(channelProfileRequest);
      return ChannelResponse.fromDto(dto);}
    catch(ConstraintViolationException e){
      throw new RuntimeException("Record already exists. Please update instead of create.");
    }
  }

  public ChannelResponse update(final ChannelRequest request) {

    var dto = request.toDto();

    return ChannelResponse.fromDto(channelRepository.update(dto));
  }

  public ChannelResponse delete(final Long channelId) {

    return channelRepository.delete(channelId)
            .map(dto -> ChannelResponse.fromDto(dto))
            .get();
  }

  public List<ChannelResponse> getAll() {

    return channelRepository
            .getAll()
            .stream()
            .map(dto -> profileLoader(ChannelResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }
  public List<ChannelResponse> getAll(String profileId) {

    return channelRepository
            .getAll()
            .stream()
            .map(dto -> {
              dto.setLoginProfileId(profileId);
              var response = profileLoader(ChannelResponse.fromDto(dto));
              return response;
            })
            .collect(Collectors.toList());
  }
  public ChannelResponse retrieveById(Long channelId) {

    return channelRepository
            .retrieveById(channelId)
            .map(dto -> profileLoader(ChannelResponse.fromDto(dto)))
            .get();
  }

  public List<ChannelResponse> search(final String searchString) {

    return channelRepository
            .search(searchString)
            .stream()
            .map(dto -> profileLoader(ChannelResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }
  public List<ChannelResponse> retrieveChannelsLinked(Long profileId) {
    var profileJoinedChannels = channelProfileService.retrieveByProfileId(profileId+"");
    var channelIDs = profileJoinedChannels.stream().map(channelProfile->Long.valueOf(channelProfile.getChannelId())).collect(Collectors.toList());
    return channelRepository.retrieveByIds(channelIDs)
            .stream()
            .map(dto -> profileLoader(ChannelResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }
  private ChannelResponse profileLoader(ChannelResponse response) {

    if (response.getCreatorProfile() != null
            && response.getCreatorProfile().getProfileId() != null) {
      response.getCreatorProfile().cloneProperties(
              profileService.retrieveProfileDtoById(
                      Long.valueOf(response.getCreatorProfile().getProfileId())));
    }
    String status = "NA";
    String loginProfileId = response.getLoginProfileId();
    if(loginProfileId != null) {
      status = "Join Channel";
      var channelProfileById = channelProfileRepository.retrieveByChannelIdAndProfileId(Long.valueOf(response.getChannelId()), Long.valueOf(loginProfileId));
      if(!channelProfileById.isEmpty()){
        status = channelProfileById.get().getStatus();
      }
    }
    var channelProfileList = channelProfileService.retrieveByChannelId(response.getChannelId());
    if (channelProfileList != null) {
      int i = 0;
      for (var channelProfile : channelProfileList) {
        if (channelProfile.getProfile() != null) {
          channelProfile.getProfile().cloneProperties(
                  profileService.retrieveProfileDtoById(
                          Long.valueOf(channelProfile.getProfile().getProfileId())));
          if(channelProfile.getStatus().matches("(?i)Accepted|Admin")){
            i++;
          }
        }
      }
      response.setAcceptedUserCount(i);
      response.setChannelProfiles(channelProfileList);
    }
    response.setLoginProfileChannelStatus(status);
    return response;
  }

}
