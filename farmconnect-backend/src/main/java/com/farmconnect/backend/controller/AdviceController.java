package com.farmconnect.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.farmconnect.backend.dto.AdviceReplyDto;
import com.farmconnect.backend.dto.AdviceRequestDto;
import com.farmconnect.backend.dto.AdviceResponseDto;
import com.farmconnect.backend.service.AdviceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdviceController {

    private final AdviceService adviceService;

    @PostMapping({"/farmer/advice-requests", "/farmer/advice-requests/{farmerId}"})
    public ResponseEntity<AdviceResponseDto> createAdviceRequest(
            @RequestBody AdviceRequestDto dto,
            Authentication authentication,
            @PathVariable(required = false) Long farmerId) {
        return ResponseEntity.ok(adviceService.createAdviceRequest(authentication.getName(), dto));
    }

    @GetMapping("/expert/advice-requests")
    public ResponseEntity<List<AdviceResponseDto>> getAllAdviceRequests() {
        return ResponseEntity.ok(adviceService.getAllAdviceRequests());
    }

    @GetMapping("/expert/advice-requests/pending")
    public ResponseEntity<List<AdviceResponseDto>> getPendingAdviceRequests() {
        return ResponseEntity.ok(adviceService.getPendingAdviceRequests());
    }

    @GetMapping("/expert/advice-requests/{id}")
    public ResponseEntity<AdviceResponseDto> getAdviceById(@PathVariable Long id) {
        return ResponseEntity.ok(adviceService.getAdviceById(id));
    }

    @PutMapping("/expert/advice-requests/{id}/reply")
    public ResponseEntity<AdviceResponseDto> replyToAdvice(
            @PathVariable Long id,
            @RequestBody AdviceReplyDto dto,
            Authentication authentication) {
        return ResponseEntity.ok(adviceService.replyToAdvice(id, dto, authentication.getName()));
    }

    @DeleteMapping("/expert/advice-requests/{id}")
    public ResponseEntity<String> deleteAdvice(@PathVariable Long id) {
        adviceService.deleteAdvice(id);
        return ResponseEntity.ok("Advice request deleted successfully");
    }

    @GetMapping({"/farmer/advice-requests", "/farmer/advice-requests/{farmerId}"})
    public ResponseEntity<List<AdviceResponseDto>> getAdviceByFarmer(Authentication authentication, @PathVariable(required = false) Long farmerId) {
        return ResponseEntity.ok(adviceService.getAdviceByFarmer(authentication.getName()));
    }

    @GetMapping({"/expert/advice/expert", "/expert/advice/expert/{expertId}"})
    public ResponseEntity<List<AdviceResponseDto>> getAdviceByExpert(Authentication authentication, @PathVariable(required = false) Long expertId) {
        return ResponseEntity.ok(adviceService.getAdviceByExpert(authentication.getName()));
    }
}
