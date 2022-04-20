package com.vire.model.response;

import com.vire.dto.SocialDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class SocialPostResponse {

    private Long socialId;
    private Long userId;
    private Long categoryId;
    private String type;
    private String subject;
    private String description;
    private String contact;
    private String alternateContact;
    private String imagePath;
    private Long createdTime;
    private Long updatedTime;
    private List<SocialSendToResponse> sendTo;
    private List<CommentResponse> comments;
    private List<CommentReplyResponse> commentsReply;
    private List<LikesResponse> likes;
    //private List<SocialPostSentResponse> sendTo;

    public static SocialPostResponse fromDto(final SocialDto dto) {
        return new ModelMapper().map(dto, SocialPostResponse.class);
    }
}
