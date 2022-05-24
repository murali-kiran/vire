package com.vire.repository;

import com.vire.dao.ExperienceReportDao;
import com.vire.dto.ExperienceReportDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExperienceReportRepository {

  @Autowired
  ExperienceReportRepositoryJpa experienceReportRepositoryJpa;

  public ExperienceReportDto create(final ExperienceReportDto experienceReportDto) {

    var experienceReportDao = ExperienceReportDao.fromDto(experienceReportDto);
    experienceReportDao.onPrePersist();

    return experienceReportRepositoryJpa.save(experienceReportDao).toDto();
  }

  public ExperienceReportDto update(final ExperienceReportDto experienceReportDto) {

    var existingObject = experienceReportRepositoryJpa.findById(experienceReportDto.getExperienceReportId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var experienceReportDao = ExperienceReportDao.fromDto(experienceReportDto);
    experienceReportDao.onPreUpdate();

    return experienceReportRepositoryJpa.save(experienceReportDao).toDto();
  }

  public Optional<ExperienceReportDto> delete(final Long experienceReportId) {

    var optionalSocial = retrieveById(experienceReportId);

    if (optionalSocial.isPresent()) {
      experienceReportRepositoryJpa.deleteById(experienceReportId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ExperienceReportDto> getAll() {

    return experienceReportRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ExperienceReportDto> retrieveById(Long experienceReportId) {

    return experienceReportRepositoryJpa.findById(experienceReportId).map(dao -> dao.toDto());
  }

  public List<ExperienceReportDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ExperienceReportDao>(searchString).resolve();

    return experienceReportRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
