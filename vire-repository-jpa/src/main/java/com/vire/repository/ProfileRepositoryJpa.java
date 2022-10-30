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

    Optional<ProfileDao> findByEmailIdAndPasswordAndProfileStatus(final String email, final  String password, final  String profileStatus);

    Optional<ProfileDao> findByMobileNumberAndPasswordAndProfileStatus(final String phonenumber, final  String password, final  String profileStatus);

    Optional<ProfileDao> findByEmailIdAndProfileStatus(final String email, final  String profileStatus);

    Optional<ProfileDao> findByMobileNumberAndProfileStatus(final String phonenumber, final  String profileStatus);

    List<ProfileDao> findByEmailIdOrMobileNumberAndProfileStatus(final String email, final String phonenumber, final  String profileStatus);

    List<ProfileDao> findByProfileIdNotIn(List<Long> profileIDs);

    List<ProfileDao> findByProfileIdIn(List<Long> profileIDs);

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE() and profile_status='active'",nativeQuery = true)
    int getTodayCreatedProfiles();

    @Query(value = "select count(*) from profile where DATE(FROM_UNIXTIME(created_time/1000)) = CURDATE() and profile_status = 'active' and profile_type = :profileType",nativeQuery = true)
    int getTodayCreatedSpecificProfiles(@Param(value = "profileType") String profileType);

    @Modifying
    @Query("update ProfileDao p set p.isBlocked = :isBlocked where p.profileId = :profileId")
    void blockProfile(@Param(value = "profileId") long profileId, @Param(value = "isBlocked") Boolean isBlocked);

    @Modifying
    @Query("update ProfileDao p set p.profileStatus = :profileStatus where p.profileId = :profileId")
    void updateProfileStatus(@Param(value = "profileId") long profileId, @Param(value = "profileStatus") String profileStatus);

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

    Long countByProfileStatus(String profileStatus);

    Long countByProfileTypeAndProfileStatus(String profileType, String profileStatus);
}
