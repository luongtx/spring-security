package io.og4dev.controller;

import io.og4dev.dto.AuthResponse;
import io.og4dev.dto.LoginRequestDto;
import io.og4dev.dto.RegisterRequestDto;
import io.og4dev.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequestDto request){
        return ResponseEntity.status(201).body(service.register(request));
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequestDto request){
        return ResponseEntity.status(200).body(service.login(request));
    }
}
