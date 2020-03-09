package com.backend.springboottemplate.repository;

import com.backend.springboottemplate.repository.entity.UserProfileEntity;

public interface UserProfileRepository extends BaseRepository<UserProfileEntity> {
    UserProfileEntity findByUserId(long userId);
}
