package com.vire.repository;

import com.vire.dao.ExperienceLikesDao;
import com.vire.dto.ExperienceLikesDto;
import com.vire.repository.ExperienceLikesRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExperienceLikesRepository {

  @Autowired
  ExperienceLikesRepositoryJpa experienceLikesRepositoryJpa;

  public ExperienceLikesDto create(final ExperienceLikesDto experienceLikesDto) {

    var experienceLikesDao = ExperienceLikesDao.fromDto(experienceLikesDto);
    experienceLikesDao.onPrePersist();

    return experienceLikesRepositoryJpa.save(experienceLikesDao).toDto();
  }

  public ExperienceLikesDto update(final ExperienceLikesDto experienceLikesDto) {

    var existingObject = experienceLikesRepositoryJpa.findById(experienceLikesDto.getExperienceLikesId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var experienceLikesDao = ExperienceLikesDao.fromDto(experienceLikesDto);
    experienceLikesDao.onPreUpdate();

    return experienceLikesRepositoryJpa.save(experienceLikesDao).toDto();
  }

  public Optional<ExperienceLikesDto> delete(final Long experienceLikesId) {

    var optionalSocial = retrieveById(experienceLikesId);

    if (optionalSocial.isPresent()) {
      experienceLikesRepositoryJpa.deleteById(experienceLikesId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ExperienceLikesDto> getAll() {

    return experienceLikesRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ExperienceLikesDto> retrieveById(Long experienceLikesId) {

    return experienceLikesRepositoryJpa.findById(experienceLikesId).map(dao -> dao.toDto());
  }

  public List<ExperienceLikesDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ExperienceLikesDao>(searchString).resolve();

    return experienceLikesRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }

    public long getLikesCountOfProfile(Long profileId) {
        return experienceLikesRepositoryJpa.countByLikerProfileId(profileId);
    }
}
