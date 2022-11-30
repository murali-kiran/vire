package com.vire.repository;

import com.vire.dao.ProfileDao;
import com.vire.dao.RequesterProfileSettingDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequesterProfileSettingRepositoryJpa
    extends JpaRepository<RequesterProfileSettingDao, Long>, JpaSpecificationExecutor<RequesterProfileSettingDao> {

    Optional<RequesterProfileSettingDao> findByProfileIdAndRequesterProfileIdAndSettingType(final Long profileId, final  Long requesterProfileId, final  String settingType);

}
