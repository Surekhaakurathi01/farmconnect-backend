package com.farmconnect.backend.dto;

import lombok.Data;

@Data
public class AdminProfileDto {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String location;
    private String department;
    private String employeeCode;
    private String designation;
    private String officeLocation;
    private String accessLevel;
    private String responsibilities;
}
