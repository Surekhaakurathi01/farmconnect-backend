package com.farmconnect.backend.dto;

import lombok.Data;

@Data
public class FarmerProfileDto {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String location;

    private String farmName;
    private String farmLocation;
    private Double landArea;
    private String soilType;
    private String mainCrop;
    private String irrigationType;
}