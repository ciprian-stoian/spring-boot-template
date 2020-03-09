package com.backend.springboottemplate.builder;

import com.backend.springboottemplate.service.dto.UserProfileDTO;

import java.time.LocalDate;
import java.util.UUID;

public class UserProfileDTOBuilder {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public UserProfileDTO build() {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUuid(uuid);
        userProfileDTO.setFirstName(firstName);
        userProfileDTO.setLastName(lastName);
        userProfileDTO.setBirthDate(birthDate);

        return userProfileDTO;
    }

    public UserProfileDTO buildDefault() {
        return new UserProfileDTOBuilder()
                .uuid(UUID.randomUUID())
                .firstName("First")
                .lastName("Last")
                .birthDate(LocalDate.now())
                .build();
    }

    public UserProfileDTOBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserProfileDTOBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserProfileDTOBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserProfileDTOBuilder birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }
}
