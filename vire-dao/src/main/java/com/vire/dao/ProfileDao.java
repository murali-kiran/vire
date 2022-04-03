package com.vire.dao;

import com.vire.dto.ProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.persistence.*;

@Entity
@Table(name = "profile")
@Data
public class ProfileDao {

    @Id
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    //@Column(name = "user_id", nullable = false)
    //private Long userId;

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

    public ProfileDto toDto() {
       return new ModelMapper().map(this,ProfileDto.class);
    }

    public static ProfileDao fromDto(final ProfileDto dto) {

        ProfileDao profileDao = new ProfileDao();
        profileDao.setId(dto.getId());
        profileDao.setAadhar(dto.getAadhar());
        profileDao.setName(dto.getName());
        profileDao.setEmailId(dto.getEmailId());
        profileDao.setIsAadharVerified(dto.getIsAadharVerified());
        //profileDao.setUserId(dto.getUserId());
        profileDao.setPassword(dto.getPassword());
        profileDao.setMobileNumber(dto.getMobileNumber());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        PersonalProfileDao personalProfileDao = modelMapper.map(dto.getPersonalProfile(),PersonalProfileDao.class);

        profileDao.setPersonalProfile(personalProfileDao);
        personalProfileDao.setProfile(profileDao);

       /* modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto,ProfileDao.class);*/

        return profileDao;
    }
}
