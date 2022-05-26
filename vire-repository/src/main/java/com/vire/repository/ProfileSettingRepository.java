package com.vire.repository;

import com.vire.dao.ProfileSettingDao;
import com.vire.dto.ProfileSettingDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileSettingRepository {

  @Autowired
  ProfileSettingRepositoryJpa profileSettingRepositoryJpa;

  public ProfileSettingDto create(final ProfileSettingDto profileSettingDto) {

    var profileSettingDao = ProfileSettingDao.fromDto(profileSettingDto);
    profileSettingDao.onPrePersist();

    return profileSettingRepositoryJpa.save(profileSettingDao).toDto();
  }

  public ProfileSettingDto update(final ProfileSettingDto profileSettingDto) {

    var existingObject = profileSettingRepositoryJpa.findById(profileSettingDto.getProfileSettingId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var profileSettingDao = ProfileSettingDao.fromDto(profileSettingDto);
    profileSettingDao.onPreUpdate();

    return profileSettingRepositoryJpa.save(profileSettingDao).toDto();
  }

  public Optional<ProfileSettingDto> delete(final Long profileSettingId) {

    var optionalSocial = retrieveById(profileSettingId);

    if (optionalSocial.isPresent()) {
      profileSettingRepositoryJpa.deleteById(profileSettingId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ProfileSettingDto> getAll() {

    return profileSettingRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ProfileSettingDto> retrieveById(Long profileSettingId) {

    return profileSettingRepositoryJpa.findById(profileSettingId).map(dao -> dao.toDto());
  }

  public List<ProfileSettingDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ProfileSettingDao>(searchString).resolve();

    return profileSettingRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
