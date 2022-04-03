package com.vire.repository;

import com.vire.dao.PersonalProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalProfileRepositoryJpa extends JpaRepository<PersonalProfileDao, Long> {
}
