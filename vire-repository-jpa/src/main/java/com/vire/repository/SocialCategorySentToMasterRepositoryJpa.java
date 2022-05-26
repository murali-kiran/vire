package com.vire.repository;

import com.vire.dao.SocialCategorySentToMasterDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialCategorySentToMasterRepositoryJpa
    extends JpaRepository<SocialCategorySentToMasterDao, Long>, JpaSpecificationExecutor<SocialCategorySentToMasterDao> {}
