package com.vire.repository;

import com.vire.dao.ChannelProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelProfileRepositoryJpa
    extends JpaRepository<ChannelProfileDao, Long>, JpaSpecificationExecutor<ChannelProfileDao> {}
