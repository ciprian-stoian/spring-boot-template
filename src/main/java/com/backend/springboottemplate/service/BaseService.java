package com.backend.springboottemplate.service;

import com.backend.springboottemplate.repository.entity.IdentifiableEntity;
import com.backend.springboottemplate.service.dto.DataTransferObject;

public interface BaseService<DTO extends DataTransferObject, Entity extends IdentifiableEntity> {
    DTO toDTO(Entity entity);

    Entity toEntity(DTO dto);
}
