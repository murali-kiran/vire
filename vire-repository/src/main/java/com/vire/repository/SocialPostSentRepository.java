package com.vire.repository;

import com.vire.dao.SocialPostSendToDao;
import com.vire.dto.SocialPostSendToDto;
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

    public SocialPostSendToDto save(final SocialPostSendToDto socialPostSendToDto) {
        return socialPostSentRepositoryJpa.save(SocialPostSendToDao.fromDto(socialPostSendToDto)).toDto();
    }
    public SocialPostSendToDto update(final SocialPostSendToDto socialPostSendToDto) {
        var existingObject = socialPostSentRepositoryJpa.findById(socialPostSendToDto.getSendToId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return socialPostSentRepositoryJpa.save(SocialPostSendToDao.fromDto(socialPostSendToDto)).toDto();
    }

    public Optional<SocialPostSendToDto> deleteSent(final Long sentId) {

        var optionalSocialComment = retrieveById(sentId);
        if (optionalSocialComment.isPresent()) {
            socialPostSentRepositoryJpa.deleteById(sentId);
        } else {
            throw new RuntimeException("Social Post sent Object not exists in DB");
        }

        return optionalSocialComment;
    }

    public Optional<SocialPostSendToDto> retrieveById(Long commentId) {

        return socialPostSentRepositoryJpa.findById(commentId).map(dao -> dao.toDto());
    }
    public List<SocialPostSendToDto> searchSent(final String searchString) {

        var spec = new CustomSpecificationResolver<SocialPostSendToDao>(searchString).resolve();

        return socialPostSentRepositoryJpa.findAll(spec).stream().map(dao -> dao.toDto()).collect(Collectors.toList());
    }

}
