package com.vire.service;

import com.vire.model.request.CommentRequest;
import com.vire.model.response.CommentResponse;
import com.vire.repository.CommentReplyReposJpa;
import com.vire.repository.CommentRepository;
import com.vire.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    Snowflake snowflake;

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentReplyReposJpa commentReplyReposJpa;
    @Autowired
    ProfileService profileService;
    public CommentResponse createComment(final CommentRequest request) {

        var dto = request.toDto();
        dto.setSocialPostCommentId(snowflake.nextId());

        return CommentResponse.fromDto(commentRepository.createComment(dto));
    }

    public CommentResponse updateComment(final CommentRequest request) {

        var dto = request.toDto();

        return CommentResponse.fromDto(commentRepository.updateComment(dto));
    }

    public CommentResponse deleteComment(final Long commentId) {

        return commentRepository.deleteComment(commentId)
                .map(dto -> CommentResponse.fromDto(dto))
                .get();
    }
   
    public CommentResponse retrieveById(Long chatId) {

        return commentRepository.retrieveById(chatId)
                .map(dto -> profileLoader(CommentResponse.fromDto(dto)))
                .get();
    }

    public List<CommentResponse> searchComments(final String searchString) {

        return commentRepository.searchComments(searchString).stream()
                .map(dto -> profileLoader(CommentResponse.fromDto(dto)))
                .collect(Collectors.toList());
    }
    private CommentResponse profileLoader(CommentResponse response){
        if (response.getCommenterProfile() != null
                && response.getCommenterProfile().getProfileId() != null) {
            response.getCommenterProfile().cloneProperties(
                    profileService.retrieveProfileDtoById(
                            Long.valueOf(response.getCommenterProfile().getProfileId())));
        }
        var replyList = response.getCommentReplyResponseList();
        if(replyList != null && !replyList.isEmpty()){
            replyList.forEach(replyResponse -> replyResponse.getReplierProfile().cloneProperties(
                    profileService.retrieveProfileDtoById(
                            Long.valueOf(replyResponse.getReplierProfile().getProfileId()))));

        }

        return response;
    }
}
