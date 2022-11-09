package com.vire.service;

import com.vire.dto.NotificationType;
import com.vire.dto.SocialNotificationType;
import com.vire.model.request.SocialChatRequest;
import com.vire.model.response.FeedCommentResponse;
import com.vire.model.response.SocialChatResponse;
import com.vire.repository.SocialChatRepository;
import com.vire.utils.Snowflake;
import com.vire.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SocialChatService {

    @Autowired
    Snowflake snowflake;

    @Autowired
    SocialChatRepository socialPostChatRepo;

    @Autowired
    ProfileService profileService;
    @Autowired
    NotificationService notificationService;

    public SocialChatResponse create(final SocialChatRequest request) {

        var dto = request.toDto();
        dto.setId(snowflake.nextId());
        var checkNewChat = search("( senderProfileId:"+request.getSenderProfileId()+" ) AND ( socialId:"+request.getSocialPostId()+" )" );
        SocialNotificationType socialNotificationType = SocialNotificationType.REPLY_CHAT;
        if(checkNewChat.isEmpty()) {
            socialNotificationType = SocialNotificationType.NEW_CHAT;
        }
        try {
            notificationService.createSocialNotification(NotificationType.SOCIAL_CHAT, Long.valueOf(request.getSenderProfileId()),
                    socialNotificationType, Long.valueOf(request.getSocialPostId()), null, null, request.getIsCreatorMessage());
        } catch (Exception e) {
            throw new RuntimeException("Social Notification failed to create for social id:"+request.getSocialPostId()+" due to "+e.getMessage() );
        }
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
            String key = socialchatResponse.getChatDate();
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
                .map(dto -> profileLoader(SocialChatResponse.fromDto(dto)))
                .collect(Collectors.toList());
    }
    private SocialChatResponse profileLoader(SocialChatResponse response) {
        if (response.getSenderMinimalProfile() != null
                && response.getSenderMinimalProfile().getProfileId() != null) {
            response.getSenderMinimalProfile().cloneProperties(
                    profileService.retrieveProfileDtoById(
                            Long.valueOf(response.getSenderMinimalProfile().getProfileId())));
        }
        return response;
    }

    public List<SocialChatResponse>  retrieveChatListByLoginUser(String loginUserId, String socialId) {
        var socialChatList = socialPostChatRepo.search(String.format("( socialCreatorProfileId:%s ) AND ( socialId:%s )", loginUserId, socialId)).stream()
                .map(dto -> profileLoader(SocialChatResponse.fromDto(dto)))
                .collect(Collectors.toList());
        Set<String> senderIds = new HashSet<>();
        List<SocialChatResponse> newSenderList = new ArrayList<>();
        for(var socialchatResponse : socialChatList){
            if(!senderIds.contains(socialchatResponse.getSenderProfileId())){
                newSenderList.add(socialchatResponse);
            }
            senderIds.add(socialchatResponse.getSenderProfileId());
        }
        return newSenderList;
    }
}
