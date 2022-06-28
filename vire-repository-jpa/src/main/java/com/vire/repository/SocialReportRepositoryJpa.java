package com.vire.repository;

import com.vire.dao.SocialReportDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialReportRepositoryJpa
    extends JpaRepository<SocialReportDao, Long>, JpaSpecificationExecutor<SocialReportDao> {
    void deleteBySocialId(Long socialId);
}
