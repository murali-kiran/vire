package com.vire.service;

import com.vire.model.request.SocialChatRequest;
import com.vire.model.response.SocialChatResponse;
import com.vire.repository.SocialChatRepository;
import com.vire.utils.Snowflake;
import com.vire.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SocialChatService {

    @Autowired
    Snowflake snowflake;

    @Autowired
    SocialChatRepository socialPostChatRepo;

    public SocialChatResponse create(final SocialChatRequest request) {

        var dto = request.toDto();
        dto.setId(snowflake.nextId());

        return SocialChatResponse.fromDto(socialPostChatRepo.create(dto));
    }

    public SocialChatResponse update(final SocialChatRequest request) {

        var dto = request.toDto();

        return SocialChatResponse.fromDto(socialPostChatRepo.update(dto));
    }

    public SocialChatResponse delete(final Long chatId) {

        return socialPostChatRepo.delete(chatId)
                .map(dto -> SocialChatResponse.fromDto(dto))
                .get();
    }

    public List<SocialChatResponse> getAll() {

        return socialPostChatRepo.getAll().stream()
                .map(dto -> SocialChatResponse.fromDto(dto))
                .collect(Collectors.toList());

    }

    public SocialChatResponse retrieveById(Long chatId) {

        return socialPostChatRepo.retrieveById(chatId)
                .map(dto -> SocialChatResponse.fromDto(dto))
                .get();
    }

    public Map<String, List<SocialChatResponse>> retrieveBySenderProfileIdAndSocialPostId(String senderProfileId, String socialPostId){
        Map<String, List<SocialChatResponse>> map = new HashMap<>();
        var searchList = search(
                String.format("( senderProfileId:%s ) AND ( socialId:%s )", senderProfileId, socialPostId));
        for(var socialchatResponse : searchList){
            String key = Utility.DdMMMYYYYTimeFormat(socialchatResponse.getCreatedTime());
            var valueList = map.get(key);
            if(!map.containsKey(key)){
                valueList = new ArrayList<>();
            }
            valueList.add(socialchatResponse);
            map.put(key, valueList);
        }
        return map;
    }

    public List<SocialChatResponse> search(final String searchString) {

        return socialPostChatRepo.search(searchString).stream()
                .map(dto -> SocialChatResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
}
