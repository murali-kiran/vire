package com.vire.repository;

import com.vire.dao.ProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepositoryJpa
        extends JpaRepository<ProfileDao, Long>, JpaSpecificationExecutor<ProfileDao> {

    public Optional<ProfileDao> findByEmailIdAndPassword(final String email, final  String password);

    public Optional<ProfileDao> findByMobileNumberAndPassword(final String phonenumber, final  String password);

    public Optional<ProfileDao> findByEmailId(final String email);

    public Optional<ProfileDao> findByMobileNumber(final String phonenumber);

}
