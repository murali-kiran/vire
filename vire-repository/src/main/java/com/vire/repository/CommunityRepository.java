package com.vire.repository;

import com.vire.dao.CommunityDao;
import com.vire.dto.CommunityDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityRepository {

  @Autowired
  CommunityRepositoryJpa communityRepositoryJpa;
  @Autowired
  CommunityProfileRepositoryJpa communityProfileRepositoryJpa;
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
    if(communityDao.getCommunityFileList() == null) {
      communityDao.setCommunityFileList(existingObject.get().getCommunityFileList());
    }
    return communityRepositoryJpa.save(communityDao).toDto();
  }

  public Optional<CommunityDto> delete(final Long communityId) {

    var optionalCommunity = retrieveById(communityId);
    if (optionalCommunity.isPresent()) {
      //communityProfileRepositoryJpa.deleteByCommunityId(communityId);
      communityRepositoryJpa.deleteById(communityId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalCommunity;
  }
  public List<CommunityDto> getAll() {

    return communityRepositoryJpa.findAll(Sort.by(Sort.Direction.DESC, "updatedTime"))
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<CommunityDto> retrieveById(Long communityId) {

    return communityRepositoryJpa.findById(communityId).map(dao -> dao.toDto());
  }

  public List<CommunityDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<CommunityDao>(searchString).resolve();

    return communityRepositoryJpa.findAll(spec, Sort.by(Sort.Direction.DESC, "updatedTime")).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

    public List<CommunityDto> retrieveByIds(List<Long> communityIDs) {
      var communityDaos= communityRepositoryJpa.findByCommunityIdIn(communityIDs, Sort.by(Sort.Direction.DESC, "updatedTime"));
      var communityDtos= communityDaos.stream()
              .map(dao -> dao.toDto())
              .collect(Collectors.toList());
      return communityDtos;
    }
  public List<CommunityDto> retrieveByProfileIdStatus(Long profileId, List<String> statusList){
    return communityRepositoryJpa.findCommunityByProfileIdStatus(profileId, statusList).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
