package com.vire.repository;

import com.vire.dao.CommentReplyDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyReposJpa extends JpaRepository<CommentReplyDao, Long> ,
        JpaSpecificationExecutor<CommentReplyDao> {

}
