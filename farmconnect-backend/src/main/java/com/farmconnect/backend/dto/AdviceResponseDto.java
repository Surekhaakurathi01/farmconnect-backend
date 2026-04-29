package com.farmconnect.backend.dto;

import lombok.Data;

@Data
public class AdviceResponseDto {
    private Long id;
    private String question;
    private String queryText;
    private String image;
    private String category;
    private String priority;
    private String fieldLocation;
    private String answer;
    private String reply;
    private String expertReply;
    private String response;
    private String status;
    private String createdAt;
    private Long farmerId;
    private String farmerName;
    private Long expertId;
    private String expertName;
}
