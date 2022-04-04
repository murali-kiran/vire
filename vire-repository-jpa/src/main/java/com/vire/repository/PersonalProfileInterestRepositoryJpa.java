package com.vire.repository;

import com.vire.dao.PersonalProfileInterestDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalProfileInterestRepositoryJpa extends JpaRepository<PersonalProfileInterestDao, Long> {
}
