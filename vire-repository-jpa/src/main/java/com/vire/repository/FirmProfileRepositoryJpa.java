package com.vire.repository;

import com.vire.dao.FirmProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmProfileRepositoryJpa extends JpaRepository<FirmProfileDao,Long> {
}
