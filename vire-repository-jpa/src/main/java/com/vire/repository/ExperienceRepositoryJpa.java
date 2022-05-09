package com.vire.repository;

import com.vire.dao.ExperienceDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepositoryJpa
    extends JpaRepository<ExperienceDao, Long>, JpaSpecificationExecutor<ExperienceDao> {}
