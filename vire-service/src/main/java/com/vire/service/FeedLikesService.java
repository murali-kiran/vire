package com.vire.service;

import com.vire.dto.FeedNotificationType;
import com.vire.dto.NotificationType;
import com.vire.model.request.FeedLikesRequest;
import com.vire.model.request.FeedNotificationRequest;
import com.vire.model.request.NotificationRequest;
import com.vire.model.response.ExperienceLikesResponse;
import com.vire.model.response.FeedLikesResponse;
import com.vire.repository.FeedLikesRepository;
import com.vire.repository.FeedsRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedLikesService {

  @Autowired
  Snowflake snowflake;

  @Autowired
  FeedLikesRepository feedLikesRepository;

  @Autowired
  ProfileService profileService;
  @Autowired
  NotificationService notificationService;
  @Autowired
  FeedsRepository feedsRepository;

  public FeedLikesResponse create(final FeedLikesRequest request) {

    var dto = request.toDto(snowflake);
    try {
      notificationService.createFeedNotification(NotificationType.FEED, feedsRepository.retrieveById(Long.valueOf(request.getFeedId())).get().getProfileId()+"", Long.valueOf(request.getLikerProfileId()), FeedNotificationType.LIKE, Long.valueOf(request.getFeedId()));
    }
    catch (Exception e){
      throw new RuntimeException("Feed not found with id:"+request.getFeedId());
    }
    return FeedLikesResponse.fromDto(feedLikesRepository.create(dto));
  }

  public FeedLikesResponse update(final FeedLikesRequest request) {

    var dto = request.toDto();

    return FeedLikesResponse.fromDto(feedLikesRepository.update(dto));
  }

  public FeedLikesResponse delete(final Long feedLikesId) {

    return feedLikesRepository.delete(feedLikesId)
            .map(dto -> FeedLikesResponse.fromDto(dto))
            .get();
  }
  public FeedLikesResponse deleteByProfileAndFeed(final Long feedId, final Long profileId) {

    List<FeedLikesResponse> feedLikesResponses = search("feedId:" + feedId + " AND likerProfileId:"+profileId);
    if(feedLikesResponses != null && !feedLikesResponses.isEmpty())
      return feedLikesRepository.delete(Long.valueOf(feedLikesResponses.get(0).getFeedLikesId()))
              .map(dto -> FeedLikesResponse.fromDto(dto))
              .get();
    else
      throw new RuntimeException("No record found with given feedId:"+feedId+" profileId:"+profileId);
  }
  public List<FeedLikesResponse> getAll() {

    return feedLikesRepository
            .getAll()
            .stream()
            .map(dto -> FeedLikesResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public FeedLikesResponse retrieveById(Long feedLikesId) {

    return feedLikesRepository
            .retrieveById(feedLikesId)
            .map(dto -> FeedLikesResponse.fromDto(dto))
            .get();
  }

  public List<FeedLikesResponse> search(final String searchString) {

    return feedLikesRepository
            .search(searchString)
            .stream()
            .map(dto -> profileLoader(FeedLikesResponse.fromDto(dto)))
            .collect(Collectors.toList());
  }
  private FeedLikesResponse profileLoader(FeedLikesResponse response) {
    if (response.getLikerProfile() != null
            && response.getLikerProfile().getProfileId() != null) {
      response.getLikerProfile().cloneProperties(
              profileService.retrieveProfileDtoById(
                      Long.valueOf(response.getLikerProfile().getProfileId())));
    }
    return response;
  }
}
