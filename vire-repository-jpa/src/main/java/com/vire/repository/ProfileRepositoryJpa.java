package com.vire.repository;

import com.vire.dao.ProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepositoryJpa
        extends JpaRepository<ProfileDao, Long>, JpaSpecificationExecutor<ProfileDao> {

    Optional<ProfileDao> findByEmailIdAndPassword(final String email, final  String password);

    Optional<ProfileDao> findByMobileNumberAndPassword(final String phonenumber, final  String password);

    Optional<ProfileDao> findByEmailId(final String email);

    Optional<ProfileDao> findByMobileNumber(final String phonenumber);

    List<ProfileDao> findByEmailIdOrMobileNumber(final String email, final String phonenumber);

    List<ProfileDao> findByProfileIdNotIn(List<Long> profileIDs);

    List<ProfileDao> findByProfileIdIn(List<Long> profileIDs);

}
