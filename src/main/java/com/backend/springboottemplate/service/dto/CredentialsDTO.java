package com.backend.springboottemplate.service.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CredentialsDTO implements Serializable {
    private static final long serialVersionUID = 2547592657813214577L;

    @NotEmpty(message = "Field username must not be null or empty!")
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
