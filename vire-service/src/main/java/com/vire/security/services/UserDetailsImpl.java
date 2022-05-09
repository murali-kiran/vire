package com.vire.security.services;

import java.util.Collection;
import java.util.Objects;
import com.vire.dao.ProfileDao;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private ProfileDao profileDao;

    public UserDetailsImpl(ProfileDao profileDao) {
        this.profileDao = profileDao;
        this.username = profileDao.getEmailId();
        this.password = profileDao.getPassword();

    }

    public static UserDetailsImpl build(ProfileDao profileDao) {
        return new UserDetailsImpl(profileDao);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(profileDao.getProfileId(), user.getProfileDao().getProfileId());
    }
}

