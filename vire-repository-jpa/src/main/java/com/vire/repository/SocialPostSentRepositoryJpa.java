package com.vire.repository;

import com.vire.dao.SocialPostSendToDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialPostSentRepositoryJpa extends JpaRepository<SocialPostSendToDao, Long> ,
        JpaSpecificationExecutor<SocialPostSendToDao> {

}
