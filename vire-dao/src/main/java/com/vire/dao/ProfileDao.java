package com.vire.dao;

import com.vire.dto.ProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.Instant;

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
