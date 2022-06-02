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

  public CommunityProfileResponse create(final CommunityProfileRequest request) {

    var dto = request.toDto(snowflake);

    return CommunityProfileResponse.fromDto(communityProfileRepository.create(dto));
  }

  public CommunityProfileResponse update(final CommunityProfileRequest request) {

    var dto = request.toDto();

    return CommunityProfileResponse.fromDto(communityProfileRepository.update(dto));
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
            .map(dto -> CommunityProfileResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
