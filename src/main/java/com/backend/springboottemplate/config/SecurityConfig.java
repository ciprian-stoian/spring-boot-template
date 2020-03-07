package com.backend.springboottemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static com.backend.springboottemplate.controller.config.RestApiPathConstants.API_BASE_PATH;
import static com.backend.springboottemplate.controller.config.RestApiPathConstants.AuthenticationController.AUTHENTICATION_PATH;
import static com.backend.springboottemplate.controller.config.RestApiPathConstants.AuthenticationController.SIGN_IN_RELATIVE_PATH;
import static com.backend.springboottemplate.controller.config.RestApiPathConstants.AuthenticationController.SIGN_UP_RELATIVE_PATH;

@Configuration
@Order(0)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] NO_AUTHENTICATION_REQUIRED_PATHS = {
            AUTHENTICATION_PATH + SIGN_UP_RELATIVE_PATH,
            AUTHENTICATION_PATH + SIGN_IN_RELATIVE_PATH
    };

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher(API_BASE_PATH + "/**").authorizeRequests().anyRequest().authenticated()
                .and().httpBasic();
    }

    @Override
    public void configure(final WebSecurity web) {
        Arrays.stream(NO_AUTHENTICATION_REQUIRED_PATHS).forEach(url -> web.ignoring().antMatchers(url));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
