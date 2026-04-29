package com.farmconnect.backend.dto;

import lombok.Data;

@Data
public class AdviceRequestDto {
    private String question;
    private String queryText;
    private String image;
    private String category;
    private String priority;
    private String fieldLocation;
    private String location;
}
