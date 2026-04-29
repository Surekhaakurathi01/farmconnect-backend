package com.farmconnect.backend.dto;

import lombok.Data;

@Data
public class ExpertProfileDto {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String location;

    private String specialization;
    private String qualification;
    private Integer experienceYears;
    private String organization;
    private String bio;
}