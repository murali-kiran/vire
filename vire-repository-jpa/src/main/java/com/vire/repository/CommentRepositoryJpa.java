package com.vire.repository;

import com.vire.dao.CommentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryJpa extends JpaRepository<CommentDao, Long> ,
        JpaSpecificationExecutor<CommentDao> {

}
