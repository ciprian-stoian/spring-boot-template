package com.backend.springboottemplate.util;

import com.backend.springboottemplate.repository.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserEntity {
    public UserEntity getAuthenticatedUser() {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedUser.getUserEntity();
    }
}
