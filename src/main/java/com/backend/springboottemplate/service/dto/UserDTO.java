package com.backend.springboottemplate.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends DataTransferObject {
    private static final long serialVersionUID = 8913842744484461227L;

    @NotEmpty(message = "Field firstName must not be null or empty!")
    private String firstName;

    @NotEmpty(message = "Field lastName must not be null or empty!")
    private String lastName;

    @NotEmpty(message = "Field username must not be null or empty!")
    private String username;

    private String password;

    private Boolean active;

    @Null(message = "Field createdOn must be null!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdOn;

    @Null(message = "Field updatedOn must be null!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate updatedOn;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(final LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }
}
