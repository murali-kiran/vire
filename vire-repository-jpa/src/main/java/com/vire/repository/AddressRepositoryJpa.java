package com.vire.repository;

import com.vire.dao.AddressDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepositoryJpa extends JpaRepository<AddressDao, Long> {
}
