package com.vire.repository;

import com.vire.dao.ExperienceCommentReplyDao;
import com.vire.dto.ExperienceCommentReplyDto;
import com.vire.repository.ExperienceCommentReplyRepositoryJpa;
import com.vire.repository.search.CustomSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExperienceCommentReplyRepository {

  @Autowired
  ExperienceCommentReplyRepositoryJpa experienceCommentReplyRepositoryJpa;
  @Autowired
  ExperienceCommentRepositoryJpa experienceCommentRepositoryJpa;
  public ExperienceCommentReplyDto create(final ExperienceCommentReplyDto experienceCommentReplyDto) {

    var experienceCommentReplyDao = ExperienceCommentReplyDao.fromDto(experienceCommentReplyDto);
    experienceCommentReplyDao.onPrePersist();
    experienceCommentReplyDao.setExperienceComment(experienceCommentRepositoryJpa.getById(experienceCommentReplyDto.getCommentId()));
    return experienceCommentReplyRepositoryJpa.save(experienceCommentReplyDao).toDto();
  }

  public ExperienceCommentReplyDto update(final ExperienceCommentReplyDto experienceCommentReplyDto) {

    var existingObject = experienceCommentReplyRepositoryJpa.findById(experienceCommentReplyDto.getExperienceCommentReplyId());

    if(existingObject.isEmpty()) {
      throw new RuntimeException("Object not exists in db to update");
    }

    var experienceCommentReplyDao = ExperienceCommentReplyDao.fromDto(experienceCommentReplyDto);
    experienceCommentReplyDao.onPreUpdate();
    experienceCommentReplyDao.setExperienceComment(experienceCommentRepositoryJpa.getById(experienceCommentReplyDto.getCommentId()));
    return experienceCommentReplyRepositoryJpa.save(experienceCommentReplyDao).toDto();
  }

  public Optional<ExperienceCommentReplyDto> delete(final Long experienceCommentReplyId) {

    var optionalSocial = retrieveById(experienceCommentReplyId);

    if (optionalSocial.isPresent()) {
      experienceCommentReplyRepositoryJpa.deleteById(experienceCommentReplyId);
    } else {
      throw new RuntimeException("Object not exists in DB to delete");
    }

    return optionalSocial;
  }
  public List<ExperienceCommentReplyDto> getAll() {

    return experienceCommentReplyRepositoryJpa.findAll()
            .stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
  public Optional<ExperienceCommentReplyDto> retrieveById(Long experienceCommentReplyId) {

    return experienceCommentReplyRepositoryJpa.findById(experienceCommentReplyId).map(dao -> dao.toDto());
  }

  public List<ExperienceCommentReplyDto> search(final String searchString) {

    var spec = new CustomSpecificationResolver<ExperienceCommentReplyDao>(searchString).resolve();

    return experienceCommentReplyRepositoryJpa.findAll(spec).stream()
            .map(dao -> dao.toDto())
            .collect(Collectors.toList());
  }
}
