package com.vire.repository;

import com.vire.dao.SocialPostLikeDao;
import com.vire.dto.SocialPostLikeDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialPostLikeRepository {

    @Autowired
    SocialPostLikeRepositoryJpa socialPostLikeRepositoryJpa;

    public SocialPostLikeDto createLike(final SocialPostLikeDto socialPostLikeDto) {
        return socialPostLikeRepositoryJpa.save(SocialPostLikeDao.fromDto(socialPostLikeDto)).toDto();
    }
    public SocialPostLikeDto updateLike(final SocialPostLikeDto socialPostLikeDto) {
        var existingObject = socialPostLikeRepositoryJpa.findById(socialPostLikeDto.getId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return socialPostLikeRepositoryJpa.save(SocialPostLikeDao.fromDto(socialPostLikeDto)).toDto();
    }

    public Optional<SocialPostLikeDto> deleteLikes(final Long socialPostCommentId) {

        var optionalSocialComment = retrieveById(socialPostCommentId);
        if (optionalSocialComment.isPresent()) {
            socialPostLikeRepositoryJpa.deleteById(socialPostCommentId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocialComment;
    }
    public List<SocialPostLikeDto> getAllLikes() {

        return socialPostLikeRepositoryJpa.findAll()
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
    public Optional<SocialPostLikeDto> retrieveById(Long commentId) {

        return socialPostLikeRepositoryJpa.findById(commentId).map(dao -> dao.toDto());
    }
    public List<SocialPostLikeDto> search(final String searchString) {

        var spec = new CustomSpecificationResolver<SocialPostLikeDao>(searchString).resolve();

        return socialPostLikeRepositoryJpa.findAll(spec).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

}
