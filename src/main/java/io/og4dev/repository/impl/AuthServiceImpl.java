package io.og4dev.repository.impl;

import io.og4dev.dto.AuthResponse;
import io.og4dev.dto.LoginRequestDto;
import io.og4dev.dto.RegisterRequestDto;
import io.og4dev.entity.UserEntity;
import io.og4dev.repository.UserRepository;
import io.og4dev.service.AuthService;
import io.og4dev.service.CustomUserDetailsService;
import io.og4dev.service.JwtService;
import io.og4dev.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public AuthResponse register(RegisterRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new IllegalArgumentException("Username already taken");
        if (userRepository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email already registered");
        Role role;
        if (request.getRole() != null) {
            role = request.getRole();
        } else {
            role = Role.ROLE_USER;
        }
        UserEntity entity = new UserEntity();
        entity.setUsername(request.getUsername());
        entity.setEmail(request.getEmail());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setRole(role);
        entity.setEnabled(true);
        userRepository.save(entity);

        UserDetails details = userDetailsService.loadUserByUsername(entity.getUsername());
        String token = jwtService.generateToken(details);
        return new AuthResponse(token, entity.getUsername(), role);
    }

    @Override
    public AuthResponse login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserDetails details = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(details);
        Role role = getRoleFromAuthorities(details.getAuthorities());
        return new AuthResponse(token, request.getUsername(), role);
    }

    @Override
    public Role getRoleFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .findFirst()
                .map(a -> Role.valueOf(a.getAuthority()))
                .orElse(Role.ROLE_USER);
    }
}
