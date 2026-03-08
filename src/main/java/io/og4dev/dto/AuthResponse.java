package io.og4dev.dto;

import io.og4dev.util.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType;
    private String username;
    private Role role;

    public AuthResponse(String token, String username, Role role) {
        this.token = token;
        this.tokenType = "Bearer";
        this.username = username;
        this.role = role;
    }
}
