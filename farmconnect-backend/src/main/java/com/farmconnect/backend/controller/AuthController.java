package com.farmconnect.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.farmconnect.backend.dto.AuthResponse;
import com.farmconnect.backend.dto.LoginRequest;
import com.farmconnect.backend.dto.RegisterRequest;
import com.farmconnect.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Validated @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Validated @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return ResponseEntity.ok(Map.of("message", authService.verifyOtp(email, otp)));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, String>> resendOtp(@RequestParam String email) {
        return ResponseEntity.ok(Map.of("message", authService.resendOtp(email)));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmailToken(@RequestBody Map<String, String> body) {
        return ResponseEntity.badRequest().body(Map.of("message", "Token verification is not enabled. Please use OTP verification."));
    }
}
