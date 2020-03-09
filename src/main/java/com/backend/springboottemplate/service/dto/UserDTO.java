package com.backend.springboottemplate.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends TrackableDataTransferObject {
    @NotEmpty(message = "Field username must not be null or empty!")
    private String username;

    @NotEmpty(message = "Field password must not be null or empty!")
    private String password;

    private Boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }
}
