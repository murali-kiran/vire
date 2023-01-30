package com.vire.repository;

import com.vire.dao.ProfileBlockDao;
import com.vire.dto.ProfileBlockDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileBlockRepository {

  @Autowired
  ProfileBlockRepositoryJpa profileBlockRepositoryJpa;

  public ProfileBlockDto create(final ProfileBlockDto profileBlockDto) {

    var profileBlockDao = ProfileBlockDao.fromDto(profileBlockDto);
    profileBlockDao.onPrePersist();

    return profileBlockRepositoryJpa.save(profileBlockDao).toDto();
  }

  public ProfileBlockDto update(final ProfileBlockDto profileBlockDto) {

    var existingObject = profileBlockRepositoryJpa.findById(profileBlockDto.getProfileBlockId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var profileBlockDao = ProfileBlockDao.fromDto(profileBlockDto);
    profileBlockDao.onPreUpdate();

    return profileBlockRepositoryJpa.save(profileBlockDao).toDto();
  }

  public Optional<ProfileBlockDto> delete(final Long profileBlockId) {

    var optionalSocial = retrieveById(profileBlockId);

    if (optionalSocial.isPresent()) {
      profileBlockRepositoryJpa.deleteById(profileBlockId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ProfileBlockDto> getAll() {

    return profileBlockRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ProfileBlockDto> retrieveById(Long profileBlockId) {

    return profileBlockRepositoryJpa.findById(profileBlockId).map(dao -> dao.toDto());
  }

  public List<ProfileBlockDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ProfileBlockDao>(searchString).resolve();

    return profileBlockRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

  public List<ProfileBlockDto> retrieveBlockedProfilesByProfileId(String profileId) {

    return search("profileId:"+profileId);
  }
}
