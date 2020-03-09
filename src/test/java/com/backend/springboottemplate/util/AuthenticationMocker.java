package com.backend.springboottemplate.util;

import com.backend.springboottemplate.repository.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationMocker {
    public static void mockAuthentication(final UserEntity userEntity) {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        AuthenticatedUser authenticatedUser = mock(AuthenticatedUser.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(authenticatedUser);
        when(authenticatedUser.getUserEntity()).thenReturn(userEntity);
    }
}
