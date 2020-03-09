package com.backend.springboottemplate.service.dto;

import java.io.Serializable;
import java.util.UUID;

public abstract class DataTransferObject implements Serializable {
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }
}
