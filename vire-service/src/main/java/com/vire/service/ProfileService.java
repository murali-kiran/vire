package com.vire.service;

import com.vire.model.request.ProfileRequest;
import com.vire.model.response.ProfileResponse;
import com.vire.repository.ProfileRepotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    ProfileRepotory ProfileRepotory;

    public ProfileResponse createProfile(final ProfileRequest request) {

        var dto = request.toDto();

        return ProfileResponse.fromDto(ProfileRepotory.createProfile(dto));
    }

    public List<ProfileResponse> getProfiles() {

        return ProfileRepotory.getAllProfiles().stream()
                .map(dto -> ProfileResponse.fromDto(dto))
                .collect(Collectors.toList());
    }
}
