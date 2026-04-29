package com.farmconnect.backend.service;

import java.util.List;

import com.farmconnect.backend.dto.AdviceReplyDto;
import com.farmconnect.backend.dto.AdviceRequestDto;
import com.farmconnect.backend.dto.AdviceResponseDto;

public interface AdviceService {

    AdviceResponseDto createAdviceRequest(String currentUserEmail, AdviceRequestDto dto);

    List<AdviceResponseDto> getAllAdviceRequests();

    AdviceResponseDto getAdviceById(Long id);

    AdviceResponseDto replyToAdvice(Long adviceId, AdviceReplyDto dto, String currentUserEmail);

    void deleteAdvice(Long id);

    List<AdviceResponseDto> getAdviceByFarmer(String currentUserEmail);

    List<AdviceResponseDto> getAdviceByExpert(String currentUserEmail);

    List<AdviceResponseDto> getPendingAdviceRequests();
}
