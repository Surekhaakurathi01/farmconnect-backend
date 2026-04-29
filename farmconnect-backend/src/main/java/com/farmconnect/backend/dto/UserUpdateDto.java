package com.farmconnect.backend.dto;

import com.farmconnect.backend.enums.Role;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String fullName;
    private String name;
    private String phone;
    private String location;
    private Role role;
    private String status;
    private String accountStatus;
    private Boolean enabled;
    private Boolean isAuthorized;
}
