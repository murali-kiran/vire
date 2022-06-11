package com.vire.repository;

import com.vire.dao.CommunityProfileDao;
import com.vire.dto.CommunityProfileDto;
import com.vire.repository.CommunityProfileRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityProfileRepository {

  @Autowired
  CommunityProfileRepositoryJpa communityProfileRepositoryJpa;

  public CommunityProfileDto create(final CommunityProfileDto communityProfileDto) {

    var communityProfileDao = CommunityProfileDao.fromDto(communityProfileDto);
    communityProfileDao.onPrePersist();

    return communityProfileRepositoryJpa.save(communityProfileDao).toDto();
  }

  public CommunityProfileDto update(final CommunityProfileDto communityProfileDto) {

    var existingObject = communityProfileRepositoryJpa.findById(communityProfileDto.getCommunityProfileId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var communityProfileDao = CommunityProfileDao.fromDto(communityProfileDto);
    communityProfileDao.onPreUpdate();
    if(communityProfileDao.getCommunityFileList() == null) {
      communityProfileDao.setCommunityFileList(existingObject.get().getCommunityFileList());
    }
    return communityProfileRepositoryJpa.save(communityProfileDao).toDto();
  }

  public Optional<CommunityProfileDto> delete(final Long communityProfileId) {

    var optionalSocial = retrieveById(communityProfileId);

    if (optionalSocial.isPresent()) {
      communityProfileRepositoryJpa.deleteById(communityProfileId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<CommunityProfileDto> getAll() {

    return communityProfileRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<CommunityProfileDto> retrieveById(Long communityProfileId) {

    return communityProfileRepositoryJpa.findById(communityProfileId).map(dao -> dao.toDto());
  }

  public List<CommunityProfileDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<CommunityProfileDao>(searchString).resolve();

    return communityProfileRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

    public Optional<CommunityProfileDto> retrieveByCommunityIdAndProfileId(Long communityId, Long profileId) {
      return communityProfileRepositoryJpa.findByCommunityIdAndProfileId(communityId,profileId).map(dao -> dao.toDto());
    }
}
