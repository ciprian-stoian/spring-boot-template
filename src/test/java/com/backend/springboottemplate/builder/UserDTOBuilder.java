package com.backend.springboottemplate.builder;

import com.backend.springboottemplate.service.dto.UserDTO;

import java.util.UUID;

public class UserDTOBuilder {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean active;

    public UserDTO build() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setActive(active);

        return userDTO;
    }

    public UserDTO buildDefault() {
        return new UserDTOBuilder()
                .uuid(UUID.randomUUID())
                .firstName("First")
                .lastName("Last")
                .username("user@name.com")
                .password("password")
                .active(true)
                .build();
    }

    public UserDTOBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserDTOBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDTOBuilder lastName(String lastName) {
        this.lastName = lastName;
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
