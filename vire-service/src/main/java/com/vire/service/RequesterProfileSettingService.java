package com.vire.service;

import com.vire.dto.NotificationType;
import com.vire.dto.ProfileNotificationType;
import com.vire.model.request.RequesterProfileSettingRequest;
import com.vire.model.response.RequesterProfileSettingResponse;
import com.vire.repository.RequesterProfileSettingRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequesterProfileSettingService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  NotificationService notificationService;

  @Autowired
  RequesterProfileSettingRepository requesterProfileSettingRepository;

  @Transactional
  public RequesterProfileSettingResponse create(final RequesterProfileSettingRequest request) {

    var dto = request.toDto(snowflake);

    notificationService.createProfileNotification(NotificationType.PROFILE, request.getRequesterProfileId(), ProfileNotificationType.REQUEST_INFO_SENT, request.getProfileId());
    return RequesterProfileSettingResponse.fromDto(requesterProfileSettingRepository.create(dto));
  }

  public RequesterProfileSettingResponse update(final RequesterProfileSettingRequest request) {

    var dto = request.toDto();

    return RequesterProfileSettingResponse.fromDto(requesterProfileSettingRepository.update(dto));
  }

  @Transactional
  public RequesterProfileSettingResponse updateEnableStatus(final RequesterProfileSettingRequest request) {

    if(request.getStatus().equalsIgnoreCase("accepted")) {
      notificationService.createProfileNotification(NotificationType.PROFILE, request.getProfileId(), ProfileNotificationType.REQUEST_INFO_ACCEPTED, request.getRequesterProfileId());
    }
    return RequesterProfileSettingResponse.fromDto(requesterProfileSettingRepository.updateEnableStatus(request));
  }

  public RequesterProfileSettingResponse delete(final Long requesterProfileSettingId) {

    return requesterProfileSettingRepository.delete(requesterProfileSettingId)
            .map(dto -> RequesterProfileSettingResponse.fromDto(dto))
            .get();
  }

  public List<RequesterProfileSettingResponse> getAll() {

    return requesterProfileSettingRepository
            .getAll()
            .stream()
            .map(dto -> RequesterProfileSettingResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public RequesterProfileSettingResponse retrieveById(Long requesterProfileSettingId) {

    return requesterProfileSettingRepository
            .retrieveById(requesterProfileSettingId)
            .map(dto -> RequesterProfileSettingResponse.fromDto(dto))
            .get();
  }

  public List<RequesterProfileSettingResponse> search(final String searchString) {

    return requesterProfileSettingRepository
            .search(searchString)
            .stream()
            .map(dto -> RequesterProfileSettingResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
