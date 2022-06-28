package com.vire.repository;

import com.vire.dao.ChannelDao;
import com.vire.dto.ChannelDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepositoryJpa
    extends JpaRepository<ChannelDao, Long>, JpaSpecificationExecutor<ChannelDao> {
    List<ChannelDao> findByChannelIdIn(List<Long> channelIDs, Sort sort);
    List<ChannelDao> findAll(Sort sort);
}
