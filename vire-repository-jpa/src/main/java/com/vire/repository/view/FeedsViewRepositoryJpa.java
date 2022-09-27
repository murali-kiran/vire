package com.vire.repository.view;

import com.vire.dao.view.FeedsViewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FeedsViewRepositoryJpa extends JpaRepository<FeedsViewDao, Long>  {


}
