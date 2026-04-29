package com.farmconnect.backend.dto;

import com.farmconnect.backend.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String fullName;
    private String name;
    private String email;
    private String phone;
    private String location;
    private Role role;
    private Boolean isEmailVerified;
    private Boolean emailVerified;
    private String accountStatus;
    private String status;
    private Boolean isAuthorized;
}
