package com.vire.model.request;

import com.vire.dto.CommentReplyDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class CommentReplyRequest implements Serializable {
    private  String id;
    @NotBlank(message = "Replier Profile id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Replier Profile id must be numeric")
    private  String commentReplierProfileId;
    @NotBlank(message = "Reply required")
    private  String reply;
    @NotBlank(message = "Comment id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Comment id must be numeric")
    private  String commentId;
    @NotBlank(message = "Social post id required")
    @Pattern(regexp="(^[0-9]*$)", message = "Social post id must be numeric")
    private String socialId;
    public CommentReplyDto toDto(){
        var dto = new CommentReplyDto();
        dto.setSocialPostCommentReplyId(this.getId() == null ? null : Long.valueOf(this.getId()));
        dto.setCommentReplierProfileId(this.getCommentReplierProfileId() == null ? null : Long.valueOf(this.getCommentReplierProfileId()));
        dto.setReply(this.getReply());
        dto.setCommentId(this.getCommentId() == null ? null : Long.valueOf(this.getCommentId()));
        dto.setSocialId(this.getSocialId() == null ? null : Long.valueOf(this.getSocialId()));
        return dto;
    }
}
