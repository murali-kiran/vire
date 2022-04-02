package com.vire.repository;

import com.vire.dao.SocialImageDao;
import com.vire.dao.SocialPostCommentDao;
import com.vire.dao.SocialPostCommentReplyDao;
import com.vire.dto.SocialImageDto;
import com.vire.dto.SocialPostCommentDto;
import com.vire.dto.SocialPostCommentReplyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocialPostCommentReplyRepository {

    @Autowired
    SocialPostCommentReplyReposJpa socialPostCommentReplyReposJpa;

    public SocialPostCommentReplyDto createReply(final SocialPostCommentReplyDto socialPostCommentReplyDto) {
        return socialPostCommentReplyReposJpa.save(SocialPostCommentReplyDao.fromDto(socialPostCommentReplyDto)).toDto();
    }

    public Optional<SocialPostCommentReplyDto> retrieveById(Long replyId) {

        return socialPostCommentReplyReposJpa.findById(replyId).map(dao -> dao.toDto());
    }
    public SocialPostCommentReplyDto updateReply(final SocialPostCommentReplyDto socialPostCommentReplyDto) {
        var existingObject = socialPostCommentReplyReposJpa.findById(socialPostCommentReplyDto.getId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return socialPostCommentReplyReposJpa.save(SocialPostCommentReplyDao.fromDto(socialPostCommentReplyDto)).toDto();
    }

    public Optional<SocialPostCommentReplyDto> deleteReply(final Long socialPostReplyId) {

        var optionalSocialComment = retrieveById(socialPostReplyId);
        if (optionalSocialComment.isPresent()) {
            socialPostCommentReplyReposJpa.deleteById(socialPostReplyId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocialComment;
    }
}
