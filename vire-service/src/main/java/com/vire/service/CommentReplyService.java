package com.vire.service;

import com.vire.model.request.CommentReplyRequest;
import com.vire.model.response.CommentReplyResponse;
import com.vire.model.response.CommentResponse;
import com.vire.repository.CommentReplyRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentReplyService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    CommentReplyRepository commentReplyRepository;

    @Autowired
    ProfileService profileService;
    public CommentReplyResponse createReply(final CommentReplyRequest request) {

        var dto = request.toDto();
        dto.setSocialPostCommentReplyId(snowflake.nextId());

        return CommentReplyResponse.fromDto(commentReplyRepository.createReply(dto));
    }

    public CommentReplyResponse updateReply(final CommentReplyRequest request) {

        var dto = request.toDto();

        return CommentReplyResponse.fromDto(commentReplyRepository.updateReply(dto));
    }

    public CommentReplyResponse deleteReply(final Long chatId) {

        return commentReplyRepository.deleteReply(chatId)
                .map(dto -> CommentReplyResponse.fromDto(dto))
                .get();
    }

    public CommentReplyResponse retrieveById(Long replyId) {

        return commentReplyRepository.retrieveById(replyId)
                .map(dto -> profileLoader(CommentReplyResponse.fromDto(dto)))
                .get();
    }

    public List<CommentReplyResponse> searchReplies(final String searchString) {

        return commentReplyRepository.searchReplies(searchString).stream()
                .map(dto -> profileLoader(CommentReplyResponse.fromDto(dto)))
                .collect(Collectors.toList());
    }

    private CommentReplyResponse profileLoader(CommentReplyResponse response){
        if (response.getReplierProfile() != null
                && response.getReplierProfile().getProfileId() != null) {
            response.getReplierProfile().cloneProperties(
                    profileService.retrieveProfileDtoById(
                            Long.valueOf(response.getReplierProfile().getProfileId())));
        }
        return response;
    }
}
