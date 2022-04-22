package com.vire.service;

import com.vire.model.request.FeedsSendToRequest;
import com.vire.model.response.FeedsSendToResponse;
import com.vire.model.response.FeedsSendToResponse;
import com.vire.repository.FeedsSendToRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedsSendToService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    FeedsSendToRepository feedsSendToRepository;

    public FeedsSendToResponse create(final FeedsSendToRequest request) {

        var dto = request.toDto();
        dto.setFeedsSendToId(snowflake.nextId());

        return FeedsSendToResponse.fromDto(feedsSendToRepository.save(dto));
    }

    public FeedsSendToResponse update(final FeedsSendToRequest request) {

        var dto = request.toDto();

        return FeedsSendToResponse.fromDto(feedsSendToRepository.update(dto));
    }

    public FeedsSendToResponse deletePostSent(final Long sentId) {

        return feedsSendToRepository.deleteSent(sentId)
                .map(dto -> FeedsSendToResponse.fromDto(dto))
                .get();
    }

    public FeedsSendToResponse retrieveById(Long likeId) {

        return feedsSendToRepository.retrieveById(likeId)
                .map(dto -> FeedsSendToResponse.fromDto(dto))
                .get();
    }

    public List<FeedsSendToResponse> searchSent(final String searchString) {

        return feedsSendToRepository.searchSent(searchString).stream()
                .map(dto -> FeedsSendToResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
}
