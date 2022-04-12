package com.vire.dao;

import com.vire.dto.ProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name = "profile")
@Data
public class ProfileDao {

    @Id
    @Column(name = "profile_id")
    private Long profileId;

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

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FirmProfileDao firmProfile;

    public ProfileDto toDto() {

    /*    ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(mapper->mapper.sk)
        Condition<?, ?> isFirmProfile = new Condition<ProfileDao, ProfileDto>() {
            public boolean applies(MappingContext<ProfileDao, ProfileDto> context) {
                return context.getSource().getFirmProfile() != null;
            }
        };

        modelMapper.addMappings(new PropertyMap<ProfileDao,ProfileDto >() {
            @Override
            protected void configure() {
                when(isFirmProfile).map().setIsPersonalProfile(false);
            }
        });



        return modelMapper.map;   */

       return new ModelMapper().map(this,ProfileDto.class);
    }

    public static ProfileDao fromDto(final ProfileDto dto) {

        ProfileDao profileDao = new ProfileDao();
        profileDao.setProfileId(dto.getProfileId());
        profileDao.setAadhar(dto.getAadhar());
        profileDao.setName(dto.getName());
        profileDao.setEmailId(dto.getEmailId());
        profileDao.setIsAadharVerified(dto.getIsAadharVerified());
        //profileDao.setUserId(dto.getUserId());
        profileDao.setPassword(dto.getPassword());
        profileDao.setMobileNumber(dto.getMobileNumber());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if(dto.getPersonalProfile()!=null) {
            PersonalProfileDao personalProfileDao = modelMapper.map(dto.getPersonalProfile(), PersonalProfileDao.class);
            profileDao.setPersonalProfile(personalProfileDao);
            personalProfileDao.setProfile(profileDao);
        }else{
            FirmProfileDao firmProfileDao = modelMapper.map(dto.getFirmProfile(), FirmProfileDao.class);
            profileDao.setFirmProfile(firmProfileDao);
            firmProfileDao.setProfile(profileDao);
        }

       /* modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto,ProfileDao.class);*/

        return profileDao;
    }
}
