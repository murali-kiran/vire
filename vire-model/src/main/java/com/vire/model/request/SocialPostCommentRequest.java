package com.vire.model.request;

import com.vire.dto.SocialPostCommentDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialPostCommentRequest implements Serializable {
    private  Long id;
    private  Long commenterProfileId;
    private  String comment;
    private  Long socialPostId;
    private  Long commentTime;
    public SocialPostCommentDto toDto(){
        var dto = new SocialPostCommentDto();
        dto.setId(this.getId());
        dto.setCommenterProfileId(this.getCommenterProfileId());
        dto.setComment(this.getComment());
        dto.setSocialPostId(this.getSocialPostId());
        dto.setCommentTime(this.getCommentTime());
        return dto;
    }
}
