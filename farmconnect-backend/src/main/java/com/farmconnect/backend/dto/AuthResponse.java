package com.farmconnect.backend.dto;

import com.farmconnect.backend.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private Long id;
    private String fullName;
    private String name;
    private String email;
    private Role role;
    private String token;
    private String accessToken;
    private String message;
    private Boolean isEmailVerified;
    private Boolean emailVerified;
    private String accountStatus;
    private String status;
    private Boolean isAuthorized;
    private Boolean requiresEmailVerification;
}
