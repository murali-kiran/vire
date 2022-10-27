package com.vire.service;

import com.vire.model.request.ProfileNotificationRequest;
import com.vire.model.response.ProfileNotificationResponse;
import com.vire.repository.ProfileNotificationRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileNotificationService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  ProfileNotificationRepository profileNotificationRepository;

  public ProfileNotificationResponse create(final ProfileNotificationRequest request) {

    var dto = request.toDto(snowflake);

    return ProfileNotificationResponse.fromDto(profileNotificationRepository.create(dto));
  }

  public ProfileNotificationResponse update(final ProfileNotificationRequest request) {

    var dto = request.toDto();

    return ProfileNotificationResponse.fromDto(profileNotificationRepository.update(dto));
  }

  public ProfileNotificationResponse delete(final Long profileNotificationId) {

    return profileNotificationRepository.delete(profileNotificationId)
            .map(dto -> ProfileNotificationResponse.fromDto(dto))
            .get();
  }

  public List<ProfileNotificationResponse> getAll() {

    return profileNotificationRepository
            .getAll()
            .stream()
            .map(dto -> ProfileNotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public ProfileNotificationResponse retrieveById(Long profileNotificationId) {

    return profileNotificationRepository
            .retrieveById(profileNotificationId)
            .map(dto -> ProfileNotificationResponse.fromDto(dto))
            .get();
  }

  public List<ProfileNotificationResponse> search(final String searchString) {

    return profileNotificationRepository
            .search(searchString)
            .stream()
            .map(dto -> ProfileNotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
