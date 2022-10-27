package com.vire.service;

import com.vire.model.request.SocialNotificationRequest;
import com.vire.model.response.SocialNotificationResponse;
import com.vire.repository.SocialNotificationRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialNotificationService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  SocialNotificationRepository socialNotificationRepository;

  public SocialNotificationResponse create(final SocialNotificationRequest request) {

    var dto = request.toDto(snowflake);

    return SocialNotificationResponse.fromDto(socialNotificationRepository.create(dto));
  }

  public SocialNotificationResponse update(final SocialNotificationRequest request) {

    var dto = request.toDto();

    return SocialNotificationResponse.fromDto(socialNotificationRepository.update(dto));
  }

  public SocialNotificationResponse delete(final Long socialNotificationId) {

    return socialNotificationRepository.delete(socialNotificationId)
            .map(dto -> SocialNotificationResponse.fromDto(dto))
            .get();
  }

  public List<SocialNotificationResponse> getAll() {

    return socialNotificationRepository
            .getAll()
            .stream()
            .map(dto -> SocialNotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public SocialNotificationResponse retrieveById(Long socialNotificationId) {

    return socialNotificationRepository
            .retrieveById(socialNotificationId)
            .map(dto -> SocialNotificationResponse.fromDto(dto))
            .get();
  }

  public List<SocialNotificationResponse> search(final String searchString) {

    return socialNotificationRepository
            .search(searchString)
            .stream()
            .map(dto -> SocialNotificationResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
