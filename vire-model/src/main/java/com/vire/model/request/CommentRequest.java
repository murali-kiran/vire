package com.vire.model.request;

import com.vire.dto.CommentDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentRequest implements Serializable {
    private  String id;
    private  String commenterProfileId;
    private  String comment;
    private  String socialPostId;
    public CommentDto toDto(){
        var dto = new CommentDto();
        dto.setSocialPostCommentId(this.getId() == null ? null : Long.valueOf(this.getId()));
        dto.setCommenterProfileId(this.getCommenterProfileId() == null ? null : Long.valueOf(this.getCommenterProfileId()));
        dto.setComment(this.getComment());
        dto.setSocialId(this.getSocialPostId() == null ? null : Long.valueOf(this.getSocialPostId()));
        return dto;
    }
}
