package com.vire.repository;

import com.vire.dao.view.SocialFileViewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialFileRepositoryJpa extends JpaRepository<SocialFileViewDao, Long> ,
        JpaSpecificationExecutor<SocialFileViewDao> {

    List<SocialFileViewDao> findBySocialId(Long socialId);
}
