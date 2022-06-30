package com.vire.repository;

import com.vire.dao.CommentDao;
import com.vire.dto.CommentDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentRepository {

    @Autowired
    CommentRepositoryJpa commentRepositoryJpa;

    public CommentDto createComment(final CommentDto commentDto) {
        var commentDao = CommentDao.fromDto(commentDto);
        commentDao.onPrePersist();
        return commentRepositoryJpa.save(commentDao).toDto();
    }
    public CommentDto updateComment(final CommentDto commentDto) {
        var existingObject = commentRepositoryJpa.findById(commentDto.getSocialPostCommentId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }
        var commentDao = CommentDao.fromDto(commentDto);
        commentDao.onPreUpdate();
        return commentRepositoryJpa.save(commentDao).toDto();
    }

    public Optional<CommentDto> deleteComment(final Long socialPostCommentId) {

        var optionalSocialComment = retrieveById(socialPostCommentId);
        if (optionalSocialComment.isPresent()) {
            commentRepositoryJpa.deleteById(socialPostCommentId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocialComment;
    }

    public Optional<CommentDto> retrieveById(Long commentId) {

        return commentRepositoryJpa.findById(commentId).map(dao -> dao.toDto());
    }
    public List<CommentDto> searchComments(final String searchString) {

        var spec = new CustomSpecificationResolver<CommentDao>(searchString).resolve();

        return commentRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

}
