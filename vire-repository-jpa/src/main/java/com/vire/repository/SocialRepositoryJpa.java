package com.vire.repository;

import com.vire.dao.SocialDao;
import com.vire.dto.SocialDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialRepositoryJpa extends JpaRepository<SocialDao, Long> , JpaSpecificationExecutor<SocialDao> {
    List<SocialDao> findByCategoryId(Long categoryId);
    List<SocialDao> getBySendTo(final String searchString);

    List<SocialDao> findAll(Sort sort);
}
