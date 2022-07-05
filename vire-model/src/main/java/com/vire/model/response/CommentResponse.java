package com.vire.model.response;

import com.vire.dto.CommentDto;
import com.vire.utils.Utility;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentResponse implements Serializable {


    private String id;
    //private  String commenterProfileId;
    private String comment;
    private String socialPostId;
    private List<CommentReplyResponse> commentReplyResponseList;
    private MinimalProfileResponse commenterProfile;
    private String commentedTime;
    private Long createdTime;
    private Long updatedTime;

    public static CommentResponse fromDto(CommentDto dto) {
        var response = new CommentResponse();
        response.setId(String.valueOf(dto.getSocialPostCommentId()));
        //response.setCommenterProfileId(String.valueOf(dto.getCommenterProfileId()));
        response.setComment(dto.getComment());
        response.setSocialPostId(String.valueOf(dto.getSocialId()));
        if (dto.getCommentReplyDtoList() != null && !dto.getCommentReplyDtoList().isEmpty()) {
            response.setCommentReplyResponseList(dto.getCommentReplyDtoList()
                    .stream()
                    .map(child -> CommentReplyResponse.fromDto(child))
                    .collect(Collectors.toList()));
            Collections.sort(response.getCommentReplyResponseList(), Comparator.comparing(CommentReplyResponse::getUpdatedTime).reversed());
        }
        if (dto.getCommenterProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getCommenterProfileId()));
            response.setCommenterProfile(minProfileRes);
        }
        response.setCommentedTime(Utility.calculateTimeDiff(dto.getUpdatedTime()));
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }

}
