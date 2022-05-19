package com.vire.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vire.dao.ProfileDao;
import com.vire.model.request.LoginRequest;
import com.vire.model.response.ProfileResponse;
import com.vire.repository.ProfileRepository;
import com.vire.security.jwt.JwtUtils;
import com.vire.security.services.UserDetailsImpl;
import com.vire.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    /*
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

     */

    public Optional<ProfileResponse> login(LoginRequest loginRequest) {

        Optional<ProfileResponse> loginResponse = Optional.empty();
        Authentication authentication = null;
        if(Utility.isEmailValid(loginRequest.getEmailOrphonenumber()) || Utility.isPhoneNumberValid(loginRequest.getEmailOrphonenumber())){
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrphonenumber(), loginRequest.getPassword()));
        }else{
            return Optional.empty();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var profileDto = userDetails.getProfileDao().toDto();
        ProfileResponse profileResponse = ProfileResponse.fromDto(profileDto);

        if("true".equalsIgnoreCase(profileDto.getFirstLogin())){
            profileDto.setFirstLogin("false");
            profileRepository.updateProfile(profileDto);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObj = mapper.createObjectNode();
            jsonObj.put("username", userDetails.getUsername());
            jsonObj.put("email", profileResponse.getEmailId());
            jsonObj.put("mobile number", profileResponse.getMobileNumber());
            String subject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);

            String jwt = jwtUtils.generateJwtToken(subject);
            profileResponse.setToken(jwt);

            return Optional.of(profileResponse);
        }catch (JsonProcessingException processingException){
            logger.error("Got error while preparing the subject",processingException.getMessage());
            return Optional.empty();
        }


    }
}