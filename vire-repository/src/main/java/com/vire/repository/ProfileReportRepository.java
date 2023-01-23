package com.vire.repository;

import com.vire.dao.ProfileReportDao;
import com.vire.dto.ProfileReportDto;
import com.vire.repository.ProfileReportRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileReportRepository {

  @Autowired
  ProfileReportRepositoryJpa profileReportRepositoryJpa;

  public ProfileReportDto create(final ProfileReportDto profileReportDto) {

    var profileReportDao = ProfileReportDao.fromDto(profileReportDto);
    profileReportDao.onPrePersist();

    return profileReportRepositoryJpa.save(profileReportDao).toDto();
  }

  public ProfileReportDto update(final ProfileReportDto profileReportDto) {

    var existingObject = profileReportRepositoryJpa.findById(profileReportDto.getProfileReportId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var profileReportDao = ProfileReportDao.fromDto(profileReportDto);
    profileReportDao.onPreUpdate();

    return profileReportRepositoryJpa.save(profileReportDao).toDto();
  }

  public Optional<ProfileReportDto> delete(final Long profileReportId) {

    var optionalSocial = retrieveById(profileReportId);

    if (optionalSocial.isPresent()) {
      profileReportRepositoryJpa.deleteById(profileReportId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ProfileReportDto> getAll() {

    return profileReportRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ProfileReportDto> retrieveById(Long profileReportId) {

    return profileReportRepositoryJpa.findById(profileReportId).map(dao -> dao.toDto());
  }

  public List<ProfileReportDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ProfileReportDao>(searchString).resolve();

    return profileReportRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
