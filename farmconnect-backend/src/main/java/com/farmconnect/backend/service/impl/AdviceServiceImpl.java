package com.farmconnect.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmconnect.backend.dto.AdviceReplyDto;
import com.farmconnect.backend.dto.AdviceRequestDto;
import com.farmconnect.backend.dto.AdviceResponseDto;
import com.farmconnect.backend.entity.Advice;
import com.farmconnect.backend.entity.User;
import com.farmconnect.backend.enums.Role;
import com.farmconnect.backend.repository.AdviceRepository;
import com.farmconnect.backend.repository.UserRepository;
import com.farmconnect.backend.service.AdviceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdviceServiceImpl implements AdviceService {

    private final AdviceRepository adviceRepository;
    private final UserRepository userRepository;

    @Override
    public AdviceResponseDto createAdviceRequest(String currentUserEmail, AdviceRequestDto dto) {
        User farmer = getRequiredUser(currentUserEmail, Role.FARMER);
        Advice advice = Advice.builder()
                .question(firstText(dto.getQuestion(), dto.getQueryText()))
                .image(dto.getImage())
                .category(firstText(dto.getCategory(), "General"))
                .priority(firstText(dto.getPriority(), "Medium"))
                .fieldLocation(firstText(dto.getFieldLocation(), dto.getLocation()))
                .status("PENDING")
                .farmer(farmer)
                .build();
        return mapToDto(adviceRepository.save(advice));
    }

    @Override
    public List<AdviceResponseDto> getAllAdviceRequests() {
        return adviceRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public AdviceResponseDto getAdviceById(Long id) {
        return mapToDto(adviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Advice request not found")));
    }

    @Override
    public AdviceResponseDto replyToAdvice(Long adviceId, AdviceReplyDto dto, String currentUserEmail) {
        Advice advice = adviceRepository.findById(adviceId).orElseThrow(() -> new RuntimeException("Advice request not found"));
        User expert = getRequiredUser(currentUserEmail, Role.EXPERT);
        advice.setAnswer(firstText(dto.getAnswer(), firstText(dto.getReply(), firstText(dto.getExpertReply(), dto.getResponse()))));
        advice.setExpert(expert);
        advice.setStatus("ANSWERED");
        return mapToDto(adviceRepository.save(advice));
    }

    @Override
    public void deleteAdvice(Long id) {
        adviceRepository.delete(adviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Advice request not found")));
    }

    @Override
    public List<AdviceResponseDto> getAdviceByFarmer(String currentUserEmail) {
        User farmer = getRequiredUser(currentUserEmail, Role.FARMER);
        return adviceRepository.findByFarmerId(farmer.getId()).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<AdviceResponseDto> getAdviceByExpert(String currentUserEmail) {
        User expert = getRequiredUser(currentUserEmail, Role.EXPERT);
        return adviceRepository.findByExpertId(expert.getId()).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<AdviceResponseDto> getPendingAdviceRequests() {
        return adviceRepository.findByStatus("PENDING").stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private User getRequiredUser(String email, Role role) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != role) throw new RuntimeException("Access denied");
        return user;
    }

    private AdviceResponseDto mapToDto(Advice advice) {
        AdviceResponseDto dto = new AdviceResponseDto();
        dto.setId(advice.getId());
        dto.setQuestion(advice.getQuestion());
        dto.setQueryText(advice.getQuestion());
        dto.setImage(advice.getImage());
        dto.setCategory(advice.getCategory());
        dto.setPriority(advice.getPriority());
        dto.setFieldLocation(advice.getFieldLocation());
        dto.setAnswer(advice.getAnswer());
        dto.setReply(advice.getAnswer());
        dto.setExpertReply(advice.getAnswer());
        dto.setResponse(advice.getAnswer());
        dto.setStatus(advice.getStatus());
        dto.setCreatedAt(advice.getCreatedAt() == null ? null : advice.getCreatedAt().toString());
        if (advice.getFarmer() != null) {
            dto.setFarmerId(advice.getFarmer().getId());
            dto.setFarmerName(advice.getFarmer().getFullName());
        }
        if (advice.getExpert() != null) {
            dto.setExpertId(advice.getExpert().getId());
            dto.setExpertName(advice.getExpert().getFullName());
        }
        return dto;
    }

    private String firstText(String primary, String fallback) {
        if (primary != null && !primary.trim().isEmpty()) return primary.trim();
        return fallback == null ? null : fallback.trim();
    }
}
