package com.vire.repository;

import com.vire.dao.ProfileNotificationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileNotificationRepositoryJpa
    extends JpaRepository<ProfileNotificationDao, Long>, JpaSpecificationExecutor<ProfileNotificationDao> {}
