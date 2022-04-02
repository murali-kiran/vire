package com.vire.repository;

import com.vire.dao.SocialPostChatDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialPostChatRepositoryJpa extends JpaRepository<SocialPostChatDao, Long> ,
        JpaSpecificationExecutor<SocialPostChatDao> {

}
