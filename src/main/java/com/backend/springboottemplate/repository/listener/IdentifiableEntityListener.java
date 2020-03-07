package com.backend.springboottemplate.repository.listener;

import com.backend.springboottemplate.repository.entity.IdentifiableEntity;

import javax.persistence.PrePersist;
import java.util.UUID;

public class IdentifiableEntityListener {

    @PrePersist
    public void prePersist(final IdentifiableEntity entity) {
        if (entity.getUuid() == null) {
            entity.setUuid(UUID.randomUUID());
        }
    }
}
