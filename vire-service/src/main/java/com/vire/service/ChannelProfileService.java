package com.vire.service;

import com.vire.model.request.ChannelProfileRequest;
import com.vire.model.response.ChannelProfileResponse;
import com.vire.repository.ChannelProfileRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelProfileService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ChannelProfileRepository channelProfileRepository;

  public ChannelProfileResponse create(final ChannelProfileRequest request) {

    var dto = request.toDto(snowflake);

    return ChannelProfileResponse.fromDto(channelProfileRepository.create(dto));
  }

  public ChannelProfileResponse update(final ChannelProfileRequest request) {

    var dto = request.toDto();

    return ChannelProfileResponse.fromDto(channelProfileRepository.update(dto));
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
            .map(dto -> ChannelProfileResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
