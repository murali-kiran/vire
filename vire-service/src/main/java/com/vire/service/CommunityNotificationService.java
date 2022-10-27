package com.vire.service;

import com.vire.model.request.CommunityNotificationRequest;
import com.vire.model.response.CommunityNotificationResponse;
import com.vire.repository.CommunityNotificationRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityNotificationService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  CommunityNotificationRepository communityNotificationRepository;

  public CommunityNotificationResponse create(final CommunityNotificationRequest request) {

    var dto = request.toDto(snowflake);

    return CommunityNotificationResponse.fromDto(communityNotificationRepository.create(dto));
  }

  public CommunityNotificationResponse update(final CommunityNotificationRequest request) {

    var dto = request.toDto();

    return CommunityNotificationResponse.fromDto(communityNotificationRepository.update(dto));
  }

  public CommunityNotificationResponse delete(final Long communityNotificationId) {

    return communityNotificationRepository.delete(communityNotificationId)
            .map(dto -> CommunityNotificationResponse.fromDto(dto))
            .get();
  }

  public List<CommunityNotificationResponse> getAll() {

    return communityNotificationRepository
            .getAll()
            .stream()
            .map(dto -> CommunityNotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public CommunityNotificationResponse retrieveById(Long communityNotificationId) {

    return communityNotificationRepository
            .retrieveById(communityNotificationId)
            .map(dto -> CommunityNotificationResponse.fromDto(dto))
            .get();
  }

  public List<CommunityNotificationResponse> search(final String searchString) {

    return communityNotificationRepository
            .search(searchString)
            .stream()
            .map(dto -> CommunityNotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
