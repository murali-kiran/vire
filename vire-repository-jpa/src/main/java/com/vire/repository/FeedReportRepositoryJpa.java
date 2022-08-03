package com.vire.repository;

import com.vire.dao.FeedReportDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedReportRepositoryJpa
    extends JpaRepository<FeedReportDao, Long>, JpaSpecificationExecutor<FeedReportDao> {

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedProfiles();
}
