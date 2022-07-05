package com.vire.repository;

import com.vire.dao.ExperienceViewsCountDao;
import com.vire.dto.ExperienceViewsCountDto;
import com.vire.repository.search.CustomSpecificationResolver;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExperienceViewsCountRepository {

    @Autowired
    ExperienceViewsCountRepositoryJpa experienceRepositoryJpa;
    @Autowired
    SessionFactory sessionFactory;

    public ExperienceViewsCountDto create(final ExperienceViewsCountDto experienceDto) {

        var experienceDao = ExperienceViewsCountDao.fromDto(experienceDto);
        //experienceDao.onPrePersist();

        return experienceRepositoryJpa.save(experienceDao).toDto();
    }

    public ExperienceViewsCountDto update(final ExperienceViewsCountDto experienceDto) {

        var existingObject = experienceRepositoryJpa.findById(experienceDto.getExperienceId());

        if (existingObject.isEmpty()) {
            throw new RuntimeException("Object not exists in db to update");
        }

        var experienceDao = ExperienceViewsCountDao.fromDto(experienceDto);
        // experienceDao.onPreUpdate();

        return experienceRepositoryJpa.save(experienceDao).toDto();
    }

    public Optional<ExperienceViewsCountDto> delete(final Long experienceId) {
        var optionalSocial = retrieveById(experienceId);
        if (optionalSocial.isPresent()) {
            experienceRepositoryJpa.deleteById(experienceId);
        } else {
            throw new RuntimeException("Object not exists in DB to delete");
        }
        return optionalSocial;
    }

    public List<ExperienceViewsCountDto> getAll() {

        return experienceRepositoryJpa.findAll().stream().map(dao -> dao.toDto()).collect(Collectors.toList());
    }

    public Optional<ExperienceViewsCountDto> retrieveById(Long experienceId) {

        return experienceRepositoryJpa.findById(experienceId).map(dao -> dao.toDto());
    }

    public List<ExperienceViewsCountDto> search(final String searchString) {

        var spec = new CustomSpecificationResolver<ExperienceViewsCountDao>(searchString).resolve();

        return experienceRepositoryJpa.findAll(spec).stream().map(dao -> dao.toDto()).collect(Collectors.toList());
    }

}
