package com.vire.model.response;

import com.vire.dto.CommentReplyDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentReplyResponse implements Serializable {
    private  String id;
    //private  String commentReplierProfileId;
    private  String reply;
    private  String commentId;
    private String socialId;
    private MinimalProfileResponse replierProfile;
    private String repliedTime;
    private Long createdTime;
    private Long updatedTime;
    public static CommentReplyResponse fromDto(CommentReplyDto dto){
        var response = new CommentReplyResponse();
        response.setId(String.valueOf(dto.getSocialPostCommentReplyId()));
        //response.setCommentReplierProfileId(String.valueOf(dto.getCommentReplierProfileId()));
        response.setReply(dto.getReply());
        response.setCommentId(String.valueOf(dto.getCommentId()));
        response.setSocialId(dto.getSocialId().toString());
        if (dto.getCommentReplierProfileId() != null) {
            var minProfileRes = new MinimalProfileResponse();
            minProfileRes.setProfileId(String.valueOf(dto.getCommentReplierProfileId()));
            response.setReplierProfile(minProfileRes);
        }
        response.setRepliedTime(calculateTimeDiff(dto.getUpdatedTime()));
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
            return "few seconds ego";
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
