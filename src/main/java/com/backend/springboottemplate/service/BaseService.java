package com.backend.springboottemplate.service;

import com.backend.springboottemplate.repository.entity.IdentifiableEntity;
import com.backend.springboottemplate.service.dto.DataTransferObject;

import java.util.UUID;

public interface BaseService<DTO extends DataTransferObject, Entity extends IdentifiableEntity> {
    void validate(DTO dto);

    DTO toDTO(Entity entity);

    Entity toEntity(DTO dto);

    Entity toEntity(DTO dto, UUID uuid);
}
