package com.vire.model.response;

import com.vire.dto.ProfileDto;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ProfileResponse {

    private Long id;
    private String name;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String aadhar;
    private String isAadharVerified;
    private PersonalProfileResponse personalProfile;

    public static ProfileResponse fromDto(final ProfileDto dto) {
        return new ModelMapper().map(dto,ProfileResponse.class);
    }
}
