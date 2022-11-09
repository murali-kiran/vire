package com.vire.repository.view;

import com.vire.dao.ProfileDao;
import com.vire.dao.view.ProfileViewDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileViewRepositoryJpa
        extends JpaRepository<ProfileViewDao, Long>, JpaSpecificationExecutor<ProfileViewDao> {

    Page<ProfileViewDao> findByProfileStatus(final String profileStatus, Pageable pageable);

    Optional<ProfileViewDao> findByProfileId(Long profileId);

}



