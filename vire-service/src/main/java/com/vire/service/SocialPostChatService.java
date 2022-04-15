package com.vire.service;

import com.vire.model.request.SocialPostChatRequest;
import com.vire.model.response.SocialPostChatResponse;
import com.vire.repository.SocialPostChatRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialPostChatService {

    @Autowired
    Snowflake snowflake;

    @Autowired
    SocialPostChatRepository socialPostChatRepo;

    public SocialPostChatResponse create(final SocialPostChatRequest request) {

        var dto = request.toDto();
        dto.setId(snowflake.nextId());

        return SocialPostChatResponse.fromDto(socialPostChatRepo.create(dto));
    }

    public SocialPostChatResponse update(final SocialPostChatRequest request) {

        var dto = request.toDto();

        return SocialPostChatResponse.fromDto(socialPostChatRepo.update(dto));
    }

    public SocialPostChatResponse delete(final Long chatId) {

        return socialPostChatRepo.delete(chatId)
                .map(dto -> SocialPostChatResponse.fromDto(dto))
                .get();
    }

    public List<SocialPostChatResponse> getAll() {

        return socialPostChatRepo.getAll().stream()
                .map(dto -> SocialPostChatResponse.fromDto(dto))
                .collect(Collectors.toList());

    }

    public SocialPostChatResponse retrieveById(Long chatId) {

        return socialPostChatRepo.retrieveById(chatId)
                .map(dto -> SocialPostChatResponse.fromDto(dto))
                .get();
    }

    public List<SocialPostChatResponse> search(final String searchString) {

        return socialPostChatRepo.search(searchString).stream()
                .map(dto -> SocialPostChatResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
}
