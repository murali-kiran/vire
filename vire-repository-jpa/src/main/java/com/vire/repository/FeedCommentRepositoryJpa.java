package com.vire.repository;

import com.vire.dao.FeedCommentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedCommentRepositoryJpa
    extends JpaRepository<FeedCommentDao, Long>, JpaSpecificationExecutor<FeedCommentDao> {}
