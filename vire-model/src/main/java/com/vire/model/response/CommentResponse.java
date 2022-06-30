package com.vire.model.response;

import com.vire.dto.CommentDto;
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
        response.setCommentedTime(calculateTimeDiff(dto.getUpdatedTime()));
        response.setCreatedTime(dto.getCreatedTime());
        response.setUpdatedTime(dto.getUpdatedTime());
        return response;
    }

    private static String calculateTimeDiff(Long updatedTime) {
        Long currentTime = System.currentTimeMillis();
        Long timeDifferenceMilliseconds = currentTime - updatedTime;
        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = timeDifferenceMilliseconds / ((long) 60 * 60 * 1000 * 24 * 365);

        if (diffSeconds < 1) {
            return "few seconds ago";
        } else if (diffMinutes < 1) {
            return diffSeconds + " s ";
        } else if (diffHours < 1) {
            return diffMinutes + " m ";
        } else if (diffDays < 1) {
            return diffHours + " h ";
        } else if (diffWeeks < 1) {
            return diffDays + " d ";
        } else if (diffMonths < 1) {
            return diffWeeks + " weeks";
        } else if (diffYears < 1) {
            return diffMonths + " months";
        } else {
            return diffYears + " years";
        }
    }
}
