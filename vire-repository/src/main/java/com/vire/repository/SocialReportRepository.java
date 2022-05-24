package com.vire.repository;

import com.vire.dao.SocialReportDao;
import com.vire.dto.SocialReportDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialReportRepository {

  @Autowired
  SocialReportRepositoryJpa socialReportRepositoryJpa;

  public SocialReportDto create(final SocialReportDto socialReportDto) {

    var socialReportDao = SocialReportDao.fromDto(socialReportDto);
    socialReportDao.onPrePersist();

    return socialReportRepositoryJpa.save(socialReportDao).toDto();
  }

  public SocialReportDto update(final SocialReportDto socialReportDto) {

    var existingObject = socialReportRepositoryJpa.findById(socialReportDto.getSocialReportId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var socialReportDao = SocialReportDao.fromDto(socialReportDto);
    socialReportDao.onPreUpdate();

    return socialReportRepositoryJpa.save(socialReportDao).toDto();
  }

  public Optional<SocialReportDto> delete(final Long socialReportId) {

    var optionalSocial = retrieveById(socialReportId);

    if (optionalSocial.isPresent()) {
      socialReportRepositoryJpa.deleteById(socialReportId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<SocialReportDto> getAll() {

    return socialReportRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<SocialReportDto> retrieveById(Long socialReportId) {

    return socialReportRepositoryJpa.findById(socialReportId).map(dao -> dao.toDto());
  }

  public List<SocialReportDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<SocialReportDao>(searchString).resolve();

    return socialReportRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
