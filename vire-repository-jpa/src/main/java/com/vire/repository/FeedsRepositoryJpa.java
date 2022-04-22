package com.vire.repository;

import com.vire.dao.FeedsDao;
import com.vire.dao.SocialDao;
import com.vire.dto.SocialDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedsRepositoryJpa extends JpaRepository<FeedsDao, Long> , JpaSpecificationExecutor<FeedsDao> {

}
