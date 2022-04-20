package com.vire.repository;

import com.vire.dao.LikesDao;
import com.vire.dao.SocialDao;
import com.vire.dto.LikesDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikesRepository {

    @Autowired
    LikesRepositoryJpa likesRepositoryJpa;

    public LikesDto createLike(final LikesDto likesDto) {
        var likesDao = LikesDao.fromDto(likesDto);
        likesDao.onPrePersist();
        return likesRepositoryJpa.save(likesDao).toDto();
    }
    public LikesDto updateLike(final LikesDto likesDto) {
        var existingObject = likesRepositoryJpa.findById(likesDto.getId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }
        var likesDao = LikesDao.fromDto(likesDto);
        likesDao.onPreUpdate();
        return likesRepositoryJpa.save(likesDao).toDto();
    }

    public Optional<LikesDto> deleteLike(final Long likeId) {

        var optionalSocialComment = retrieveById(likeId);
        if (optionalSocialComment.isPresent()) {
            likesRepositoryJpa.deleteById(likeId);
        } else {
            throw new RuntimeException("like Object not exists in DB");
        }

        return optionalSocialComment;
    }

    public Optional<LikesDto> retrieveById(Long likeId) {

        return likesRepositoryJpa.findById(likeId).map(dao -> dao.toDto());
    }
    public List<LikesDto> searchLikes(final String searchString) {

        var spec = new CustomSpecificationResolver<LikesDao>(searchString).resolve();

        return likesRepositoryJpa.findAll(spec).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

}
