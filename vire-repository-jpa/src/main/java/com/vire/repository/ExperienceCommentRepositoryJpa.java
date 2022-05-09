package com.vire.repository;

import com.vire.dao.ExperienceCommentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceCommentRepositoryJpa
    extends JpaRepository<ExperienceCommentDao, Long>, JpaSpecificationExecutor<ExperienceCommentDao> {}
