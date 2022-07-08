package com.vire.repository;

import com.vire.dao.FeedCommentReplyDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedCommentReplyRepositoryJpa
    extends JpaRepository<FeedCommentReplyDao, Long>, JpaSpecificationExecutor<FeedCommentReplyDao> {
    Integer countByFeedId(Long feedId);
}
