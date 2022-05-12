package com.vire.repository;

import com.vire.dao.ExperienceDao;
import com.vire.dto.ExperienceDto;
import com.vire.repository.ExperienceRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExperienceRepository {

  @Autowired
  ExperienceRepositoryJpa experienceRepositoryJpa;

  public ExperienceDto create(final ExperienceDto experienceDto) {

    var experienceDao = ExperienceDao.fromDto(experienceDto);
    experienceDao.onPrePersist();

    return experienceRepositoryJpa.save(experienceDao).toDto();
  }

  public ExperienceDto update(final ExperienceDto experienceDto) {

    var existingObject = experienceRepositoryJpa.findById(experienceDto.getExperienceId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var experienceDao = ExperienceDao.fromDto(experienceDto);
    experienceDao.onPreUpdate();

    return experienceRepositoryJpa.save(experienceDao).toDto();
  }

  public Optional<ExperienceDto> delete(final Long experienceId) {

    var optionalSocial = retrieveById(experienceId);

    if (optionalSocial.isPresent()) {
      experienceRepositoryJpa.deleteById(experienceId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ExperienceDto> getAll() {

    return experienceRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ExperienceDto> retrieveById(Long experienceId) {

    return experienceRepositoryJpa.findById(experienceId).map(dao -> dao.toDto());
  }

  public List<ExperienceDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ExperienceDao>(searchString).resolve();

    return experienceRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
