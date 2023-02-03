package com.vire.repository;

import com.vire.dao.AdminMessageDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMessageRepositoryJpa
    extends JpaRepository<AdminMessageDao, Long>, JpaSpecificationExecutor<AdminMessageDao> {}
