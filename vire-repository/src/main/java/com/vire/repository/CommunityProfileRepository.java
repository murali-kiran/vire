package com.vire.repository;

import com.vire.dao.CommunityProfileDao;
import com.vire.dto.CommunityProfileDto;
import com.vire.model.request.CommunityProfileRequest;
import com.vire.repository.search.CustomSpecificationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommunityProfileRepository {

  @Autowired
  CommunityProfileRepositoryJpa communityProfileRepositoryJpa;
  @Autowired
  CommunityProfileFileRepositoryJpa communityProfileFileRepositoryJpa;

  public CommunityProfileDto create(final CommunityProfileDto communityProfileDto) {

    var communityProfileDao = CommunityProfileDao.fromDto(communityProfileDto);
    communityProfileDao.onPrePersist();

    return communityProfileRepositoryJpa.save(communityProfileDao).toDto();
  }

  @Transactional
  public CommunityProfileDto update(final CommunityProfileDto communityProfileDto) {

    /*var existingObject = communityProfileRepositoryJpa.findById(communityProfileDto.getCommunityProfileId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }*/
    if(Arrays.asList("Rejected", "Exit", "Blocked").contains(communityProfileDto.getStatus())){
      var files = communityProfileFileRepositoryJpa.findByCommunityProfileId(communityProfileDto.getCommunityProfileId());
      var fileIds = files.stream().map(f -> f.getCommunityProfileFileId()).collect(Collectors.toList());
      for(var fileid: fileIds){
        communityProfileFileRepositoryJpa.deleteById(fileid);
      }
    }
    var communityProfileDao = CommunityProfileDao.fromDto(communityProfileDto);
    communityProfileDao.onPreUpdate();
    /*if(communityProfileDao.getCommunityFileList() == null)  {
      log.info("#############PROFILE STATUS:"+communityProfileDao.getStatus());
      communityProfileDao.setCommunityFileList(existingObject.get().getCommunityFileList());
    }*/
    /*if (Arrays.asList("Rejected", "Exit", "Blocked").contains(communityProfileDao.getStatus())){
      communityProfileDao.setCommunityFileList(null);
    }*/
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

    public List<CommunityProfileDto> deleteByCommunityID(Long communityId) {
      return communityProfileRepositoryJpa.deleteByCommunityId(communityId).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
    }
}
