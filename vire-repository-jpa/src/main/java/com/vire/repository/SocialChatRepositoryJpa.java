package com.vire.repository;

import com.vire.dao.SocialChatDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialChatRepositoryJpa extends JpaRepository<SocialChatDao, Long> ,
        JpaSpecificationExecutor<SocialChatDao> {

}
