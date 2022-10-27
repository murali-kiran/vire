package com.vire.repository;

import com.vire.dao.FeedNotificationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedNotificationRepositoryJpa
    extends JpaRepository<FeedNotificationDao, Long>, JpaSpecificationExecutor<FeedNotificationDao> {}
