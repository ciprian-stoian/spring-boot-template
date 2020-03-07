package com.backend.springboottemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<Entity> extends JpaRepository<Entity, Long> {
    Entity findByUuid(UUID uuid);
}
