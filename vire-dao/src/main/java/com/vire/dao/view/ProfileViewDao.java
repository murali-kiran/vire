package com.vire.dao.view;

import com.vire.dao.FirmProfileDao;
import com.vire.dao.PersonalProfileDao;
import com.vire.dao.ProfileSettingDao;
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
public class ProfileViewDao {

    @Id
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_blocked", columnDefinition="BOOLEAN DEFAULT false")
    private Boolean isBlocked = false;

    @Column(name="profile_type", nullable = true)
    private String profileType;

    @Column(name = "created_time", nullable = false, updatable = false)
    public Long createdTime;

    @Column(name = "updated_time", nullable = false)
    public Long updatedTime;

    public ProfileDto toDto() {

        ProfileDto profileDto = new ProfileDto();
        profileDto.setProfileId(this.profileId);
        profileDto.setName(this.name);
        profileDto.setIsBlocked(this.isBlocked);
        profileDto.setProfileType(this.profileType);
        return profileDto;

    }

}
