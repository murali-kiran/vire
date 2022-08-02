package com.vire.repository;

import com.vire.dao.ProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time)) = CURDATE()",nativeQuery = true)
    int getTodayCreatedProfiles();

    long countByCreatedTime(long currentTimeMillis);

    @Modifying
    @Query("update ProfileDao p set p.password = :password where p.emailId = :email and p.profileId = :profileId")
    void updatePasswordViaEmailAndProfileId(@Param(value = "profileId") Long profileId,@Param(value = "email") String email, @Param(value = "password") String password);

    @Modifying
    @Query("update ProfileDao p set p.password = :password where p.mobileNumber = :phonenumber and p.profileId = :profileId")
    void updatePasswordViaPhoneNumberAndProfileId(@Param(value = "profileId") Long profileId,@Param(value = "phonenumber") String phonenumber, @Param(value = "password") String password);

    @Modifying
    @Query("update ProfileDao p set p.emailId = :newEmail where p.emailId = :oldEmail and p.profileId = :profileId")
    void updateEmailViaOldEmailAndProfileId(@Param(value = "profileId") Long profileId,@Param(value = "oldEmail") String oldEmail, @Param(value = "newEmail") String newEmail);

    @Modifying
    @Query("update ProfileDao p set p.emailId = :newEmail where p.mobileNumber = :phonenumber and p.profileId = :profileId")
    void updateEmailViaMobileNumberAndProfileId(@Param(value = "profileId") Long profileId,@Param(value = "phonenumber") String phonenumber, @Param(value = "newEmail") String newEmail);

}
