package com.backend.springboottemplate.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.UUID;

public class UserProfileDTO extends DataTransferObject {
    private static final long serialVersionUID = 4208421230155691542L;

    @Null(message = "Field userUuid must be null!")
    private UUID userUuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Field birthDate must not be null or empty!")
    private LocalDate birthDate;

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final UUID userUuid) {
        this.userUuid = userUuid;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
