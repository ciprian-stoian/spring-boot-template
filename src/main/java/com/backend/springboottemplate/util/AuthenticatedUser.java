package com.backend.springboottemplate.util;

import com.backend.springboottemplate.repository.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthenticatedUser extends User {
    private UserEntity userEntity;

    public AuthenticatedUser(final String username, final String password, final Collection<? extends GrantedAuthority> authorities,
                             final UserEntity userEntity) {
        super(username, password, authorities);
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
