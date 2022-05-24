package com.vire.repository;

import com.vire.dao.ChannelDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepositoryJpa
    extends JpaRepository<ChannelDao, Long>, JpaSpecificationExecutor<ChannelDao> {}
