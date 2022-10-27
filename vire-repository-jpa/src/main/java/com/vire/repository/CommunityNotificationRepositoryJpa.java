package com.vire.repository;

import com.vire.dao.CommunityNotificationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityNotificationRepositoryJpa
    extends JpaRepository<CommunityNotificationDao, Long>, JpaSpecificationExecutor<CommunityNotificationDao> {}
