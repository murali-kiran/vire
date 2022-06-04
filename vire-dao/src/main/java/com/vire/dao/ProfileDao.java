package com.vire.dao;

import com.vire.dto.ProfileDto;
import com.vire.enumeration.Gender;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
@Data
public class ProfileDao {

    @Id
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email_id", nullable = false)
    private String emailId;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "aadhar", nullable = false)
    private String aadhar;

    @Column(name = "is_aadhar_verified", nullable = false)
    private String isAadharVerified;

    @Column(name = "date_of_birth", nullable = true)
    private String dateOfBirth;

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="first_login", nullable = false)
    private String firstLogin;

    @Column(name = "file_id", nullable = true)
    private Long fileId;

    @Column(name = "weightage", nullable = true)
    private Integer profileWeightage;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileSettingDao> profileSettings;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PersonalProfileDao personalProfile;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FirmProfileDao firmProfile;

    @Column(name = "created_time", nullable = false, updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(Instant.now().toEpochMilli());
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedTime(Instant.now().toEpochMilli());
    }

    public ProfileDto toDto() {
        return new ModelMapper().map(this, ProfileDto.class);
    }

    public static ProfileDao fromDto(final ProfileDto dto) {

        ProfileDao profileDao = new ProfileDao();
        profileDao.setProfileId(dto.getProfileId());
        profileDao.setAadhar(dto.getAadhar());
        profileDao.setName(dto.getName());
        profileDao.setEmailId(dto.getEmailId());
        profileDao.setIsAadharVerified(dto.getIsAadharVerified());
        profileDao.setPassword(dto.getPassword());
        profileDao.setMobileNumber(dto.getMobileNumber());
        profileDao.setFileId(dto.getFileId());
        profileDao.setFirstLogin(dto.getFirstLogin());
        profileDao.setProfileWeightage(dto.getProfileWeightage());

        if (dto.getProfileSettings()!=null && !dto.getProfileSettings().isEmpty()) {
            var profileSettings = new ArrayList<ProfileSettingDao>();
            for (var profileSettingDto : dto.getProfileSettings()) {
                var profileSettingDao = new ProfileSettingDao();
                profileSettingDao.setProfileSettingId(profileSettingDto.getProfileSettingId());
                profileSettingDao.setSettingType(profileSettingDto.getSettingType());
                profileSettingDao.setIsEnable(profileSettingDto.getIsEnable());
                profileSettings.add(profileSettingDao);
            }
            profileDao.setProfileSettings(profileSettings);
        }

        if(!StringUtils.isBlank(dto.getDateOfBirth())){
            profileDao.setDateOfBirth(dto.getDateOfBirth());
        }

        if(dto.getGender()!=null){
            profileDao.setGender(dto.getGender());
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if (dto.getPersonalProfile() != null) {
            PersonalProfileDao personalProfileDao = modelMapper.map(dto.getPersonalProfile(), PersonalProfileDao.class);
            profileDao.setPersonalProfile(personalProfileDao);
            personalProfileDao.setProfile(profileDao);
        } else {
            FirmProfileDao firmProfileDao = modelMapper.map(dto.getFirmProfile(), FirmProfileDao.class);
            profileDao.setFirmProfile(firmProfileDao);
            firmProfileDao.setProfile(profileDao);
        }

        return profileDao;
    }
}
