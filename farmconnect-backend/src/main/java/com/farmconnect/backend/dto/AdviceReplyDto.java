package com.farmconnect.backend.dto;

import lombok.Data;

@Data
public class AdviceReplyDto {
    private String answer;
    private String reply;
    private String expertReply;
    private String response;
    private Long expertId;
}
