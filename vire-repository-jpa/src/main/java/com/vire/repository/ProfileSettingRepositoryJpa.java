package com.vire.repository;

import com.vire.dao.ProfileSettingDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileSettingRepositoryJpa
    extends JpaRepository<ProfileSettingDao, Long>, JpaSpecificationExecutor<ProfileSettingDao> {}
