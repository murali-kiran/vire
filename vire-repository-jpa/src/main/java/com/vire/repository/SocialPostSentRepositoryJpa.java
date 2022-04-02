package com.vire.repository;

import com.vire.dao.SocialPostLikeDao;
import com.vire.dao.SocialPostSentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialPostSentRepositoryJpa extends JpaRepository<SocialPostSentDao, Long> ,
        JpaSpecificationExecutor<SocialPostSentDao> {

}
