package com.backend.springboottemplate.repository.entity;

import com.backend.springboottemplate.repository.listener.IdentifiableEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(IdentifiableEntityListener.class)
public abstract class IdentifiableEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "id_generator")
    private Long id;

    private UUID uuid;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }
}
