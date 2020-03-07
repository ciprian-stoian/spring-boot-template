package com.backend.springboottemplate.builder;

import com.backend.springboottemplate.service.dto.CredentialsDTO;

public class CredentialsDTOBuilder {
    private String username;
    private String password;

    public CredentialsDTO build() {
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setUsername(username);
        credentialsDTO.setPassword(password);

        return credentialsDTO;
    }

    public CredentialsDTO buildDefault() {
        return new CredentialsDTOBuilder()
                .username("user@name.com")
                .password("password")
                .build();
    }

    public CredentialsDTOBuilder username(String username) {
        this.username = username;
        return this;
    }

    public CredentialsDTOBuilder password(String password) {
        this.password = password;
        return this;
    }
}
