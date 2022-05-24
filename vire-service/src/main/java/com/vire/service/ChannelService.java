package com.vire.service;

import com.vire.model.request.ChannelRequest;
import com.vire.model.response.ChannelResponse;
import com.vire.repository.ChannelRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ChannelRepository channelRepository;

  public ChannelResponse create(final ChannelRequest request) {

    var dto = request.toDto(snowflake);

    return ChannelResponse.fromDto(channelRepository.create(dto));
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
            .map(dto -> ChannelResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ChannelResponse retrieveById(Long channelId) {

    return channelRepository
            .retrieveById(channelId)
            .map(dto -> ChannelResponse.fromDto(dto))
            .get();
  }

  public List<ChannelResponse> search(final String searchString) {

    return channelRepository
            .search(searchString)
            .stream()
            .map(dto -> ChannelResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
