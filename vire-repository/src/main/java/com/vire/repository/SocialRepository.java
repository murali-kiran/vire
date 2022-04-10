package com.vire.repository;

import com.vire.dao.SocialDao;
import com.vire.dao.SocialPostSendToDao;
import com.vire.dto.SocialDto;
import com.vire.dto.SocialPostSendToDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialRepository {

    @Autowired
    SocialRepositoryJpa socialRepositoryJpa;
    @Autowired
    SocialPostSentRepository socialPostSentRepository;

    public SocialDto createSocial(final SocialDto socialDto) {
        var socialDao = SocialDao.fromDto(socialDto);
        //socialDao.getSendTo().get(0).getSocial().setSocialId(socialDao.getSocialId());
        return socialRepositoryJpa.save(socialDao).toDto();
    }
    public SocialDto updateSocial(final SocialDto socialDto) {
        var existingObject = socialRepositoryJpa.findById(socialDto.getSocialId());

        if(existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        return socialRepositoryJpa.save(SocialDao.fromDto(socialDto)).toDto();
    }

    public Optional<SocialDto> deleteSocialPost(final Long socialId) {

        var optionalSocial = retrieveById(socialId);

        if (optionalSocial.isPresent()) {
            socialRepositoryJpa.deleteById(socialId);
        } else {
            throw new RuntimeException("Social Post Object not exists in DB");
        }

        return optionalSocial;
    }
    public List<SocialDto> getAllSocials() {

        return socialRepositoryJpa.findAll()
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }
    public Optional<SocialDto> retrieveById(Long socialId) {

        return socialRepositoryJpa.findById(socialId).map(dao -> dao.toDto());
    }
    public List<SocialDto> getPostsByCommunity(Long communityId) {

        return socialRepositoryJpa.findByCategoryId(communityId)
                .stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

    public List<SocialDto> search(final String searchString) {

        var spec = new CustomSpecificationResolver<SocialDao>(searchString).resolve();

        return socialRepositoryJpa.findAll(spec).stream()
                .map(dao -> dao.toDto())
                .collect(Collectors.toList());
    }

}
