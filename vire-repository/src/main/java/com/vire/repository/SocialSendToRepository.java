package com.vire.repository;

import com.vire.dao.SocialSendToDao;
import com.vire.dto.SocialSendToDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialSendToRepository {

    @Autowired
    SocialPostSentRepositoryJpa socialPostSentRepositoryJpa;

    public SocialSendToDto save(final SocialSendToDto socialSendToDto) {
        var socialSendToDao = SocialSendToDao.fromDto(socialSendToDto);
        socialSendToDao.onPrePersist();
        return socialPostSentRepositoryJpa.save(socialSendToDao).toDto();
    }
    public SocialSendToDto update(final SocialSendToDto socialSendToDto) {
        var existingObject = socialPostSentRepositoryJpa.findById(socialSendToDto.getSendToId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return socialPostSentRepositoryJpa.save(SocialSendToDao.fromDto(socialSendToDto)).toDto();
    }

    public Optional<SocialSendToDto> deleteSent(final Long sentId) {

        var optionalSocialComment = retrieveById(sentId);
        if (optionalSocialComment.isPresent()) {
            socialPostSentRepositoryJpa.deleteById(sentId);
        } else {
            throw new RuntimeException("Social Post sent Object not exists in DB");
        }

        return optionalSocialComment;
    }

    public Optional<SocialSendToDto> retrieveById(Long commentId) {

        return socialPostSentRepositoryJpa.findById(commentId).map(dao -> dao.toDto());
    }
    public List<SocialSendToDto> searchSent(final String searchString) {

        var spec = new CustomSpecificationResolver<SocialSendToDao>(searchString).resolve();

        return socialPostSentRepositoryJpa.findAll(spec).stream().map(dao -> dao.toDto()).collect(Collectors.toList());
    }

}
