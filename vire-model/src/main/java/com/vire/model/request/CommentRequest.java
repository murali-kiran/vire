package com.vire.model.request;

import com.vire.dto.CommentDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class CommentRequest implements Serializable {
    private  String id;
    @NotBlank(message = "Commenter Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Commenter Profile id must be numeric")
    private  String commenterProfileId;
    @NotBlank(message = "Comment required")
    private  String comment;
    @NotBlank(message = "Social post id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Social post id must be numeric")
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
