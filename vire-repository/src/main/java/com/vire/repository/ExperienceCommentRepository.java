package com.vire.repository;

import com.vire.dao.ExperienceCommentDao;
import com.vire.dto.ExperienceCommentDto;
import com.vire.repository.ExperienceCommentRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExperienceCommentRepository {

  @Autowired
  ExperienceCommentRepositoryJpa experienceCommentRepositoryJpa;

  public ExperienceCommentDto create(final ExperienceCommentDto experienceCommentDto) {

    var experienceCommentDao = ExperienceCommentDao.fromDto(experienceCommentDto);
    experienceCommentDao.onPrePersist();

    return experienceCommentRepositoryJpa.save(experienceCommentDao).toDto();
  }

  public ExperienceCommentDto update(final ExperienceCommentDto experienceCommentDto) {

    var existingObject = experienceCommentRepositoryJpa.findById(experienceCommentDto.getExperienceCommentId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var experienceCommentDao = ExperienceCommentDao.fromDto(experienceCommentDto);
    experienceCommentDao.onPreUpdate();

    return experienceCommentRepositoryJpa.save(experienceCommentDao).toDto();
  }

  public Optional<ExperienceCommentDto> delete(final Long experienceCommentId) {

    var optionalSocial = retrieveById(experienceCommentId);

    if (optionalSocial.isPresent()) {
      experienceCommentRepositoryJpa.deleteById(experienceCommentId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ExperienceCommentDto> getAll() {

    return experienceCommentRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ExperienceCommentDto> retrieveById(Long experienceCommentId) {

    return experienceCommentRepositoryJpa.findById(experienceCommentId).map(dao -> dao.toDto());
  }

  public List<ExperienceCommentDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ExperienceCommentDao>(searchString).resolve();

    return experienceCommentRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
