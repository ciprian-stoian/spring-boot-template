package com.backend.springboottemplate.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
@SequenceGenerator(name = "id_generator", sequenceName = "user_profiles_id_seq", allocationSize = 1)
public class UserProfileEntity extends IdentifiableEntity {
    private static final long serialVersionUID = 7879519909156718293L;

    @Column(name = "user_uuid", nullable = false)
    private UUID userUuid;

    @Column(name = "birth_date", nullable = false)
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
