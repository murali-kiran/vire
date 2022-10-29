package com.vire.service;

import com.vire.dto.FeedNotificationType;
import com.vire.dto.NotificationType;
import com.vire.dto.SocialNotificationType;
import com.vire.model.request.SocialCallRequestRequest;
import com.vire.model.response.SocialCallRequestResponse;
import com.vire.repository.SocialCallRequestRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialCallRequestService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  SocialCallRequestRepository socialCallRequestRepository;
  @Autowired
  NotificationService notificationService;

  public SocialCallRequestResponse create(final SocialCallRequestRequest request) {

    var dto = request.toDto(snowflake);
    try {
      notificationService.createSocialNotification(NotificationType.SOCIAL, Long.valueOf(request.getRequesterProfileId()),
              request.getStatus().equalsIgnoreCase("requested")?SocialNotificationType.CALL_REQUESTED:SocialNotificationType.CALL_ACCEPTED, Long.valueOf(request.getSocialId()));
    }
    catch (Exception e){
      throw new RuntimeException("Social Notification failed to create for social id:"+request.getSocialId()+" due to "+e.getMessage() );
    }
    return SocialCallRequestResponse.fromDto(socialCallRequestRepository.create(dto));
  }

  public SocialCallRequestResponse update(final SocialCallRequestRequest request) {

    var dto = request.toDto();

    return SocialCallRequestResponse.fromDto(socialCallRequestRepository.update(dto));
  }

  public SocialCallRequestResponse delete(final Long socialCallRequestId) {

    return socialCallRequestRepository.delete(socialCallRequestId)
            .map(dto -> SocialCallRequestResponse.fromDto(dto))
            .get();
  }

  public List<SocialCallRequestResponse> getAll() {

    return socialCallRequestRepository
            .getAll()
            .stream()
            .map(dto -> SocialCallRequestResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public SocialCallRequestResponse retrieveById(Long socialCallRequestId) {

    return socialCallRequestRepository
            .retrieveById(socialCallRequestId)
            .map(dto -> SocialCallRequestResponse.fromDto(dto))
            .get();
  }

  public List<SocialCallRequestResponse> search(final String searchString) {

    return socialCallRequestRepository
            .search(searchString)
            .stream()
            .map(dto -> SocialCallRequestResponse.fromDto(dto))
            .collect(Collectors.toList());
  }
}
