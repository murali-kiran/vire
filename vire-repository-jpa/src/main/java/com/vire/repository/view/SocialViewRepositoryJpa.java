package com.vire.repository.view;

import com.vire.dao.SocialDao;
import com.vire.dao.view.SocialViewDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialViewRepositoryJpa extends JpaRepository<SocialViewDao, Long> , JpaSpecificationExecutor<SocialViewDao> {
    List<SocialViewDao> findAll(Sort sort);
    Page<SocialViewDao> findByDeletedTimeIsNull(Pageable pageable);
}
