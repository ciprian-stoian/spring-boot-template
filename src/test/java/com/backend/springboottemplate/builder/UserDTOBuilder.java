package com.backend.springboottemplate.builder;

import com.backend.springboottemplate.service.dto.UserDTO;

import java.util.UUID;

public class UserDTOBuilder {
    private UUID uuid;
    private String username;
    private String password;
    private boolean active;

    public UserDTO build() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setActive(active);

        return userDTO;
    }

    public UserDTO buildDefault() {
        return new UserDTOBuilder()
                .uuid(UUID.randomUUID())
                .username("user@name.com")
                .password("password")
                .active(true)
                .build();
    }

    public UserDTOBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserDTOBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserDTOBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserDTOBuilder active(boolean active) {
        this.active = active;
        return this;
    }
}
