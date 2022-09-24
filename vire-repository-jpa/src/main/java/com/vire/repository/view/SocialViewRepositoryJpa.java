package com.vire.repository.view;

import com.vire.dao.view.SocialViewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialViewRepositoryJpa  extends JpaRepository<SocialViewDao, Long> {



}
