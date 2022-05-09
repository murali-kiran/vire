package com.vire.repository;

import com.vire.dao.ExperienceCommentReplyDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceCommentReplyRepositoryJpa
    extends JpaRepository<ExperienceCommentReplyDao, Long>, JpaSpecificationExecutor<ExperienceCommentReplyDao> {}
