package com.vire.service;

import com.vire.model.request.SocialSendToRequest;
import com.vire.model.response.SocialSendToResponse;
import com.vire.repository.SocialSendToRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialSendToService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    SocialSendToRepository socialSendToRepository;

    public SocialSendToResponse create(final SocialSendToRequest request) {

        var dto = request.toDto();
        dto.setSendToId(snowflake.nextId());

        return SocialSendToResponse.fromDto(socialSendToRepository.save(dto));
    }

    public SocialSendToResponse update(final SocialSendToRequest request) {

        var dto = request.toDto();

        return SocialSendToResponse.fromDto(socialSendToRepository.update(dto));
    }

    public SocialSendToResponse deletePostSent(final Long sentId) {

        return socialSendToRepository.deleteSent(sentId)
                .map(dto -> SocialSendToResponse.fromDto(dto))
                .get();
    }

    public SocialSendToResponse retrieveById(Long likeId) {

        return socialSendToRepository.retrieveById(likeId)
                .map(dto -> SocialSendToResponse.fromDto(dto))
                .get();
    }

    public List<SocialSendToResponse> searchSent(final String searchString) {

        return socialSendToRepository.searchSent(searchString).stream()
                .map(dto -> SocialSendToResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
}
