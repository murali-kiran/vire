package com.vire.model.request;

import com.vire.dto.CommentDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentRequest implements Serializable {
    private  Long id;
    private  Long commenterProfileId;
    private  String comment;
    private  Long socialPostId;
    private  Long commentTime;
    public CommentDto toDto(){
        var dto = new CommentDto();
        dto.setId(this.getId());
        dto.setCommenterProfileId(this.getCommenterProfileId());
        dto.setComment(this.getComment());
        dto.setSocialPostId(this.getSocialPostId());
        dto.setCommentTime(this.getCommentTime());
        return dto;
    }
}
