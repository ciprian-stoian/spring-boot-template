package com.backend.springboottemplate.builder;

import com.backend.springboottemplate.service.dto.UserProfileDTO;

import java.time.LocalDate;
import java.util.UUID;

public class UserProfileDTOBuilder {
    private UUID uuid;
    private UUID userUuid;
    private LocalDate birthDate;

    public UserProfileDTO build() {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUuid(uuid);
        userProfileDTO.setUserUuid(userUuid);
        userProfileDTO.setBirthDate(birthDate);

        return userProfileDTO;
    }

    public UserProfileDTO buildDefault() {
        return new UserProfileDTOBuilder()
                .uuid(UUID.randomUUID())
                .userUuid(UUID.randomUUID())
                .birthDate(LocalDate.now())
                .build();
    }

    public UserProfileDTOBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserProfileDTOBuilder userUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public UserProfileDTOBuilder birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }
}
