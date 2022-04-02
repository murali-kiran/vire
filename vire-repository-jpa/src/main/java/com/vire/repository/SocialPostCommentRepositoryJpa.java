package com.vire.repository;

import com.vire.dao.SocialPostCommentDao;
import com.vire.dao.SocialPostCommentReplyDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialPostCommentRepositoryJpa extends JpaRepository<SocialPostCommentDao, Long> ,
        JpaSpecificationExecutor<SocialPostCommentDao> {

}
