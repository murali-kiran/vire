package com.vire.repository;

import com.vire.dao.SocialCategoryMasterDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialCategoryMasterRepositoryJpa
    extends JpaRepository<SocialCategoryMasterDao, Long>, JpaSpecificationExecutor<SocialCategoryMasterDao> {}
