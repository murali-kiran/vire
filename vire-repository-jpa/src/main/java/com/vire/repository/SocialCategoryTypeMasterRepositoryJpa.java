package com.vire.repository;

import com.vire.dao.SocialCategoryTypeMasterDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialCategoryTypeMasterRepositoryJpa
    extends JpaRepository<SocialCategoryTypeMasterDao, Long>, JpaSpecificationExecutor<SocialCategoryTypeMasterDao> {}
