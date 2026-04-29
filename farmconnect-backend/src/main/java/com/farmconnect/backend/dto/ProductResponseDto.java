package com.farmconnect.backend.dto;

import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String cropName;
    private String category;
    private Double price;
    private Double pricePerUnit;
    private Integer quantity;
    private String description;
    private String notes;
    private String location;
    private String pickupLocation;
    private String harvestDate;
    private String availableFrom;
    private String createdAt;
    private Long farmerId;
    private String farmerName;
    private String seller;
}
