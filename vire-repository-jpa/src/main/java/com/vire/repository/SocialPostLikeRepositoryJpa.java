package com.vire.repository;

import com.vire.dao.SocialPostCommentDao;
import com.vire.dao.SocialPostLikeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialPostLikeRepositoryJpa extends JpaRepository<SocialPostLikeDao, Long> ,
        JpaSpecificationExecutor<SocialPostLikeDao> {

}
