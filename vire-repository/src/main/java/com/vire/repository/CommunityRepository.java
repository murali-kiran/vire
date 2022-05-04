package com.vire.repository;

import com.vire.dao.CommunityDao;
import com.vire.dto.CommunityDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityRepository {

  @Autowired
  CommunityRepositoryJpa communityRepositoryJpa;

  public CommunityDto create(final CommunityDto communityDto) {

    var communityDao = CommunityDao.fromDto(communityDto);
    communityDao.onPrePersist();

    return communityRepositoryJpa.save(communityDao).toDto();
  }

  public CommunityDto update(final CommunityDto communityDto) {

    var existingObject = communityRepositoryJpa.findById(communityDto.getCommunityId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var communityDao = CommunityDao.fromDto(communityDto);
    communityDao.onPreUpdate();

    return communityRepositoryJpa.save(communityDao).toDto();
  }

  public Optional<CommunityDto> delete(final Long communityId) {

    var optionalSocial = retrieveById(communityId);

    if (optionalSocial.isPresent()) {
      communityRepositoryJpa.deleteById(communityId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<CommunityDto> getAll() {

    return communityRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<CommunityDto> retrieveById(Long communityId) {

    return communityRepositoryJpa.findById(communityId).map(dao -> dao.toDto());
  }

  public List<CommunityDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<CommunityDao>(searchString).resolve();

    return communityRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
