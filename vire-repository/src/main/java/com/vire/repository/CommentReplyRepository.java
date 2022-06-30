package com.vire.repository;

import com.vire.dao.CommentReplyDao;
import com.vire.dto.CommentReplyDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentReplyRepository {

    @Autowired
    CommentReplyReposJpa commentReplyReposJpa;
    @Autowired
    CommentRepositoryJpa commentRepositoryJpa;

    public CommentReplyDto createReply(final CommentReplyDto commentReplyDto) {
        var commentReplyDao = CommentReplyDao.fromDto(commentReplyDto);
        commentReplyDao.onPrePersist();
        commentReplyDao.setSocialPostComment(commentRepositoryJpa.getById(commentReplyDto.getCommentId()));
        return commentReplyReposJpa.save(commentReplyDao).toDto();
    }

    public Optional<CommentReplyDto> retrieveById(Long replyId) {

        return commentReplyReposJpa.findById(replyId).map(dao -> dao.toDto());
    }
    public CommentReplyDto updateReply(final CommentReplyDto commentReplyDto) {
        var existingObject = commentReplyReposJpa.findById(commentReplyDto.getSocialPostCommentReplyId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }
        var commentReplyDao = CommentReplyDao.fromDto(commentReplyDto);
        commentReplyDao.onPreUpdate();
        return commentReplyReposJpa.save(commentReplyDao).toDto();
    }

    public Optional<CommentReplyDto> deleteReply(final Long socialPostReplyId) {

        var optionalSocialComment = retrieveById(socialPostReplyId);
        if (optionalSocialComment.isPresent()) {
            commentReplyReposJpa.deleteById(socialPostReplyId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocialComment;
    }
    public List<CommentReplyDto> searchReplies(final String searchString) {

        var spec = new CustomSpecificationResolver<CommentReplyDao>(searchString).resolve();

        return commentReplyReposJpa.findAll(spec).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
}
