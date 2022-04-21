package com.vire.service;

import com.vire.dao.ProfileDao;
import com.vire.model.request.LoginRequest;
import com.vire.model.response.ProfileResponse;
import com.vire.repository.ProfileRepository;
import com.vire.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    ProfileRepository profileRepository;

    public Optional<ProfileResponse> login(LoginRequest loginRequest) {

        Optional<ProfileDao> profileDao = null;
        Optional<ProfileResponse> loginResponse = Optional.empty();
        if(Utility.isEmailValid(loginRequest.getEmailOrphonenumber())){
            profileDao =   profileRepository.loginWithEmail(loginRequest.getEmailOrphonenumber(),loginRequest.getPassword());
        }else if(Utility.isPhoneNumberValid(loginRequest.getEmailOrphonenumber())){
            profileDao = profileRepository.loginWithPhoneNumber(loginRequest.getEmailOrphonenumber(),loginRequest.getPassword());
        }else{
            return loginResponse;
        }

        if(profileDao.isPresent()){
            var profileId = profileDao.get().getProfileId();
            loginResponse = profileRepository.retrieveProfileById(profileId).map(dto-> ProfileResponse.fromDto(dto));
        }

        return loginResponse;

    }
}
