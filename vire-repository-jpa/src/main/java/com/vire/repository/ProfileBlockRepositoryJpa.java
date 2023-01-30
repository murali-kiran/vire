package com.vire.repository;

import com.vire.dao.ProfileBlockDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileBlockRepositoryJpa
    extends JpaRepository<ProfileBlockDao, Long>, JpaSpecificationExecutor<ProfileBlockDao> {}
