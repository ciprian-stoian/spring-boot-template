package com.backend.springboottemplate.repository;

import com.backend.springboottemplate.repository.entity.UserProfileEntity;

import java.util.UUID;

public interface UserProfileRepository extends BaseRepository<UserProfileEntity> {
    UserProfileEntity findByUserUuid(UUID userUuid);
}
