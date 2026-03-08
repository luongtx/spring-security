package io.og4dev.service;

import io.og4dev.dto.AuthResponse;
import io.og4dev.dto.LoginRequestDto;
import io.og4dev.dto.RegisterRequestDto;
import io.og4dev.util.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthService {
    AuthResponse register(RegisterRequestDto request);
    AuthResponse login(LoginRequestDto request);
    Role getRoleFromAuthorities(Collection<? extends GrantedAuthority> authorities);
}
