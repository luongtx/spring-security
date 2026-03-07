package io.og4dev.repository.impl;

import io.og4dev.entity.UserEntity;
import io.og4dev.repository.UserRepository;
import io.og4dev.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate template;

    @Override
    public UserEntity findByUsername(String username) {
        return template.queryForObject("SELECT * FROM users WHERE username=?", (rs, rowNum) -> {
            if (rowNum > 0) {
                return new UserEntity(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getBoolean("enabled")
                );
            } else {
                return null;
            }
        }, username);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return template.queryForObject("SELECT * FROM users WHERE email=?", (rs, rowNum) -> {
            if (rowNum > 0) {
                return new UserEntity(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getBoolean("enabled")
                );
            } else {
                return null;
            }
        }, email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        template.update("INSERT INTO users (username, email, password, enabled) VALUES (?,?,?,?)",
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getEnabled());
        return findByUsername(userEntity.getUsername());
    }
}
