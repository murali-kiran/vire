package com.vire.repository;

import com.vire.dao.SocialPostSentDao;
import com.vire.dto.SocialPostSentDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialPostSentRepository {

    @Autowired
    SocialPostSentRepositoryJpa socialPostSentRepositoryJpa;

    public SocialPostSentDto createSent(final SocialPostSentDto socialPostSentDto) {
        return socialPostSentRepositoryJpa.save(SocialPostSentDao.fromDto(socialPostSentDto)).toDto();
    }
    public SocialPostSentDto updateSent(final SocialPostSentDto socialPostSentDto) {
        var existingObject = socialPostSentRepositoryJpa.findById(socialPostSentDto.getId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return socialPostSentRepositoryJpa.save(SocialPostSentDao.fromDto(socialPostSentDto)).toDto();
    }

    public Optional<SocialPostSentDto> deleteSents(final Long socialPostCommentId) {

        var optionalSocialComment = retrieveById(socialPostCommentId);
        if (optionalSocialComment.isPresent()) {
            socialPostSentRepositoryJpa.deleteById(socialPostCommentId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocialComment;
    }
    public List<SocialPostSentDto> getAllSents() {

        return socialPostSentRepositoryJpa.findAll()
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
    public Optional<SocialPostSentDto> retrieveById(Long commentId) {

        return socialPostSentRepositoryJpa.findById(commentId).map(dao -> dao.toDto());
    }
    public List<SocialPostSentDto> search(final String searchString) {

        var spec = new CustomSpecificationResolver<SocialPostSentDao>(searchString).resolve();

        return socialPostSentRepositoryJpa.findAll(spec).stream().map(dao -> dao.toDto()).collect(Collectors.toList());
    }

}
