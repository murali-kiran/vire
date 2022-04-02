package com.vire.repository;

import com.vire.dao.SocialDao;
import com.vire.dao.SocialPostCommentDao;
import com.vire.dao.SocialPostCommentDao;
import com.vire.dao.SocialPostCommentReplyDao;
import com.vire.dto.SocialDto;
import com.vire.dto.SocialPostCommentDto;
import com.vire.dto.SocialPostCommentReplyDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialPostCommentRepository {

    @Autowired
    SocialPostCommentRepositoryJpa socialPostCommentRepositoryJpa;

    public SocialPostCommentDto createComment(final SocialPostCommentDto socialPostCommentDto) {
        return socialPostCommentRepositoryJpa.save(SocialPostCommentDao.fromDto(socialPostCommentDto)).toDto();
    }
    public SocialPostCommentDto updateComment(final SocialPostCommentDto socialPostCommentDto) {
        var existingObject = socialPostCommentRepositoryJpa.findById(socialPostCommentDto.getId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return socialPostCommentRepositoryJpa.save(SocialPostCommentDao.fromDto(socialPostCommentDto)).toDto();
    }

    public Optional<SocialPostCommentDto> deleteComment(final Long socialPostCommentId) {

        var optionalSocialComment = retrieveById(socialPostCommentId);
        if (optionalSocialComment.isPresent()) {
            socialPostCommentRepositoryJpa.deleteById(socialPostCommentId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocialComment;
    }
    public List<SocialPostCommentDto> getAllComments() {

        return socialPostCommentRepositoryJpa.findAll()
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
    public Optional<SocialPostCommentDto> retrieveById(Long commentId) {

        return socialPostCommentRepositoryJpa.findById(commentId).map(dao -> dao.toDto());
    }
    public List<SocialPostCommentDto> search(final String searchString) {

        var spec = new CustomSpecificationResolver<SocialPostCommentDao>(searchString).resolve();

        return socialPostCommentRepositoryJpa.findAll(spec).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

}
