package io.og4dev.repository;

import io.og4dev.entity.UserEntity;

public interface UserRepository {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserEntity save(UserEntity userEntity);
}
