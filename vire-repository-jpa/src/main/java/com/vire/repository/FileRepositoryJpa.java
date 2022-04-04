package com.vire.repository;

import com.vire.dao.SocialDao;
import com.vire.dao.SocialImageDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialImageRepositoryJpa extends JpaRepository<SocialImageDao, Long> , JpaSpecificationExecutor<SocialImageDao> {
}
