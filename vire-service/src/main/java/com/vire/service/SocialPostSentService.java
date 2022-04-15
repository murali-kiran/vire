package com.vire.service;

import com.vire.model.request.SocialPostSentRequest;
import com.vire.model.response.SocialPostSentResponse;
import com.vire.repository.SocialPostSentRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialPostSentService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    SocialPostSentRepository socialPostSentRepository;

    public SocialPostSentResponse create(final SocialPostSentRequest request) {

        var dto = request.toDto();
        dto.setSendToId(snowflake.nextId());

        return SocialPostSentResponse.fromDto(socialPostSentRepository.save(dto));
    }

    public SocialPostSentResponse update(final SocialPostSentRequest request) {

        var dto = request.toDto();

        return SocialPostSentResponse.fromDto(socialPostSentRepository.update(dto));
    }

    public SocialPostSentResponse deletePostSent(final Long sentId) {

        return socialPostSentRepository.deleteSent(sentId)
                .map(dto -> SocialPostSentResponse.fromDto(dto))
                .get();
    }

    public SocialPostSentResponse retrieveById(Long likeId) {

        return socialPostSentRepository.retrieveById(likeId)
                .map(dto -> SocialPostSentResponse.fromDto(dto))
                .get();
    }

    public List<SocialPostSentResponse> searchSent(final String searchString) {

        return socialPostSentRepository.searchSent(searchString).stream()
                .map(dto -> SocialPostSentResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
}
