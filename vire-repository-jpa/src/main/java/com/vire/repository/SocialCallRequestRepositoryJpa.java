package com.vire.repository;

import com.vire.dao.SocialCallRequestDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialCallRequestRepositoryJpa
    extends JpaRepository<SocialCallRequestDao, Long>, JpaSpecificationExecutor<SocialCallRequestDao> {
    void deleteBySocialId(Long socialId);
}
