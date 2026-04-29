package com.farmconnect.backend.dto;

import com.farmconnect.backend.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    // Frontend sends fullName. Some older service code may send name, so backend accepts both.
    private String fullName;
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String phone;
    private String location;

    @NotNull
    private Role role;
}
