package com.vire.repository;

import com.vire.dao.SocialDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialRepositoryJpa extends JpaRepository<SocialDao, Long> , JpaSpecificationExecutor<SocialDao> {
    List<SocialDao> findByCategoryId(Long categoryId);
}
