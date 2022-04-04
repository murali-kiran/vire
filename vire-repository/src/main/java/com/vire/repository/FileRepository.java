package com.vire.repository;

import com.vire.dao.SocialDao;
import com.vire.dao.SocialImageDao;
import com.vire.dto.SocialDto;
import com.vire.dto.SocialImageDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialImageRepository {

    @Autowired
    SocialImageRepositoryJpa socialImageRepositoryJpa;

    public SocialImageDto uploadImage(final SocialImageDto socialImageDto) {
        return socialImageRepositoryJpa.save(SocialImageDao.fromDto(socialImageDto)).toDto();
    }

    public Optional<SocialImageDto> retrieveById(Long socialId) {

        return socialImageRepositoryJpa.findById(socialId).map(dao -> dao.toDto());
    }

}
