package com.vire.repository;

import com.vire.dao.NotificationDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepositoryJpa
    extends JpaRepository<NotificationDao, Long>, JpaSpecificationExecutor<NotificationDao> {
    Optional<NotificationDao> findAllByDeletedTimeIsNull(Specification<NotificationDao> spec);
}
