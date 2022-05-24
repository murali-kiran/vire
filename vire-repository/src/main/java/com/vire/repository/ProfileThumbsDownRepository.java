package com.vire.repository;

import com.vire.dao.ProfileThumbsDownDao;
import com.vire.dto.ProfileThumbsDownDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileThumbsDownRepository {

  @Autowired
  ProfileThumbsDownRepositoryJpa profileThumbsDownRepositoryJpa;

  public ProfileThumbsDownDto create(final ProfileThumbsDownDto profileThumbsDownDto) {

    var profileThumbsDownDao = ProfileThumbsDownDao.fromDto(profileThumbsDownDto);
    profileThumbsDownDao.onPrePersist();

    return profileThumbsDownRepositoryJpa.save(profileThumbsDownDao).toDto();
  }

  public ProfileThumbsDownDto update(final ProfileThumbsDownDto profileThumbsDownDto) {

    var existingObject = profileThumbsDownRepositoryJpa.findById(profileThumbsDownDto.getProfileThumbsDownId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var profileThumbsDownDao = ProfileThumbsDownDao.fromDto(profileThumbsDownDto);
    profileThumbsDownDao.onPreUpdate();

    return profileThumbsDownRepositoryJpa.save(profileThumbsDownDao).toDto();
  }

  public Optional<ProfileThumbsDownDto> delete(final Long profileThumbsDownId) {

    var optionalSocial = retrieveById(profileThumbsDownId);

    if (optionalSocial.isPresent()) {
      profileThumbsDownRepositoryJpa.deleteById(profileThumbsDownId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ProfileThumbsDownDto> getAll() {

    return profileThumbsDownRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ProfileThumbsDownDto> retrieveById(Long profileThumbsDownId) {

    return profileThumbsDownRepositoryJpa.findById(profileThumbsDownId).map(dao -> dao.toDto());
  }

  public List<ProfileThumbsDownDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ProfileThumbsDownDao>(searchString).resolve();

    return profileThumbsDownRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
