package com.vire.repository;

import com.vire.dao.ProfileThumbsUpDao;
import com.vire.dto.ProfileThumbsUpDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileThumbsUpRepository {

  @Autowired
  ProfileThumbsUpRepositoryJpa profileThumbsUpRepositoryJpa;

  public ProfileThumbsUpDto create(final ProfileThumbsUpDto profileThumbsUpDto) {

    var profileThumbsUpDao = ProfileThumbsUpDao.fromDto(profileThumbsUpDto);
    profileThumbsUpDao.onPrePersist();

    return profileThumbsUpRepositoryJpa.save(profileThumbsUpDao).toDto();
  }

  public ProfileThumbsUpDto update(final ProfileThumbsUpDto profileThumbsUpDto) {

    var existingObject = profileThumbsUpRepositoryJpa.findById(profileThumbsUpDto.getProfileThumbsUpId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var profileThumbsUpDao = ProfileThumbsUpDao.fromDto(profileThumbsUpDto);
    profileThumbsUpDao.onPreUpdate();

    return profileThumbsUpRepositoryJpa.save(profileThumbsUpDao).toDto();
  }

  public Optional<ProfileThumbsUpDto> delete(final Long profileThumbsUpId) {

    var optionalSocial = retrieveById(profileThumbsUpId);

    if (optionalSocial.isPresent()) {
      profileThumbsUpRepositoryJpa.deleteById(profileThumbsUpId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ProfileThumbsUpDto> getAll() {

    return profileThumbsUpRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ProfileThumbsUpDto> retrieveById(Long profileThumbsUpId) {

    return profileThumbsUpRepositoryJpa.findById(profileThumbsUpId).map(dao -> dao.toDto());
  }

  public List<ProfileThumbsUpDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ProfileThumbsUpDao>(searchString).resolve();

    return profileThumbsUpRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

    public long getThumbsUpCountOfProfile(Long profileId) {
      return profileThumbsUpRepositoryJpa.countByProfileId(profileId);
    }
}
