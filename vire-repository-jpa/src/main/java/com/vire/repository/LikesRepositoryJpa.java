package com.vire.repository;

import com.vire.dao.LikesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepositoryJpa extends JpaRepository<LikesDao, Long> ,
        JpaSpecificationExecutor<LikesDao> {

}
