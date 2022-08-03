package com.vire.repository;

import com.vire.dao.FirmProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmProfileRepositoryJpa extends JpaRepository<FirmProfileDao,Long> {

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedFirmAccounts();
}
