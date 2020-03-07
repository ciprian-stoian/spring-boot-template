package com.backend.springboottemplate.repository;

import com.backend.springboottemplate.repository.entity.UserEntity;

public interface UserRepository extends BaseRepository<UserEntity> {
    UserEntity findByUsernameAndActiveTrue(String username);
}
