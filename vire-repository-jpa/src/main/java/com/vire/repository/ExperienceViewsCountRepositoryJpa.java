package com.vire.repository;

import com.vire.dao.ExperienceDao;
import com.vire.dao.ExperienceViewsCountDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceViewsCountRepositoryJpa
    extends JpaRepository<ExperienceViewsCountDao, Long>, JpaSpecificationExecutor<ExperienceViewsCountDao> {
    Optional<ExperienceViewsCountDao> findByExperienceIdAndProfileId(Long experienceId, Long profileId);
    Long countByExperienceId(Long experienceId);
}
