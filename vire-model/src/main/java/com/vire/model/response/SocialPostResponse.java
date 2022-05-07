package com.vire.model.response;

import com.vire.dto.SocialDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SocialPostResponse {

    private String socialId;
    private String profileId;
    private String categoryId;
    private String type;
    private String subject;
    private String description;
    private String contact;
    private String alternateContact;
    private String fileId;
    private Long createdTime;
    private Long updatedTime;
    private List<SocialSendToResponse> sendTo;
    private List<CommentResponse> comments;
    private List<CommentReplyResponse> commentsReply;
    private List<LikesResponse> likes;
    //private List<SocialPostSentResponse> sendTo;

    public static SocialPostResponse fromDto(final SocialDto dto) {
        var response = new SocialPostResponse();
        response.setSocialId(String.valueOf(dto.getSocialId()));
        response.setProfileId(String.valueOf(dto.getProfileId()));
        response.setCategoryId(String.valueOf(dto.getCategoryId()));
        response.setType(dto.getType());
        response.setSubject(dto.getSubject());
        response.setDescription(dto.getDescription());
        response.setContact(dto.getContact());
        response.setAlternateContact(dto.getAlternateContact());
        response.setFileId(String.valueOf(dto.getFileId()));
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        if (dto.getSendTo() != null && !dto.getSendTo().isEmpty()) {
            response.setSendTo(dto.getSendTo()
                    .stream()
                    .map(sendToDto -> SocialSendToResponse.fromDto(sendToDto))
                    .collect(Collectors.toList())
            );
        }

        return response;
    }
}
