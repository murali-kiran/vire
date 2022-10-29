package com.vire.service;

import com.vire.dto.FeedNotificationType;
import com.vire.dto.NotificationType;
import com.vire.model.request.FeedCommentReplyRequest;
import com.vire.model.response.FeedCommentReplyResponse;
import com.vire.repository.FeedCommentReplyRepository;
import com.vire.repository.FeedsRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedCommentReplyService {

  @Autowired
  Snowflake snowflake;
  @Autowired
  FeedCommentReplyRepository feedCommentReplyRepository;
  @Autowired
  NotificationService notificationService;
  @Autowired
  FeedsRepository feedsRepository;

  public FeedCommentReplyResponse create(final FeedCommentReplyRequest request) {

    var dto = request.toDto(snowflake);
    try {
      notificationService.createFeedNotification(NotificationType.FEED, Long.valueOf(request.getReplierProfileId()), FeedNotificationType.COMMENT_REPLY, Long.valueOf(request.getFeedId()));
    }
    catch (Exception e){
      throw new RuntimeException("Feed not found with id:"+request.getFeedId());
    }
    return FeedCommentReplyResponse.fromDto(feedCommentReplyRepository.create(dto));
  }

  public FeedCommentReplyResponse update(final FeedCommentReplyRequest request) {

    var dto = request.toDto();

    return FeedCommentReplyResponse.fromDto(feedCommentReplyRepository.update(dto));
  }

  public FeedCommentReplyResponse delete(final Long feedCommentReplyId) {

    return feedCommentReplyRepository.delete(feedCommentReplyId)
            .map(dto -> FeedCommentReplyResponse.fromDto(dto))
            .get();
  }

  public List<FeedCommentReplyResponse> getAll() {

    return feedCommentReplyRepository
            .getAll()
            .stream()
            .map(dto -> FeedCommentReplyResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public FeedCommentReplyResponse retrieveById(Long feedCommentReplyId) {

    return feedCommentReplyRepository
            .retrieveById(feedCommentReplyId)
            .map(dto -> FeedCommentReplyResponse.fromDto(dto))
            .get();
  }

  public List<FeedCommentReplyResponse> search(final String searchString) {

    return feedCommentReplyRepository
            .search(searchString)
            .stream()
            .map(dto -> FeedCommentReplyResponse.fromDto(dto))
            .collect(Collectors.toList());
  }

  public Integer countByFeedId(Long feedId){
    return feedCommentReplyRepository.countByFeedId(feedId);
  }
}
