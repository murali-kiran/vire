package com.vire.security.services;

import com.vire.dao.ProfileDao;
import com.vire.repository.ProfileRepository;
import com.vire.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    ProfileRepository profileRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ProfileDao> profileDao = Optional.empty();
        if(Utility.isEmailValid(username)){
            profileDao =   profileRepository.findByEmailId(username);
            profileDao =   profileRepository.findByEmailId(username);
        }else if(Utility.isPhoneNumberValid(username)){
            profileDao = profileRepository.findByMobileNumber(username);
        }

        ProfileDao profile = profileDao.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(profile);
    }

}
