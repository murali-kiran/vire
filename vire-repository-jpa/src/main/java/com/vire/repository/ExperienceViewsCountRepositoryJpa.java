package com.vire.repository;

import com.vire.dao.ExperienceDao;
import com.vire.dao.ExperienceViewsCountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceViewsCountRepositoryJpa
    extends JpaRepository<ExperienceViewsCountDao, Long>, JpaSpecificationExecutor<ExperienceViewsCountDao> {}
