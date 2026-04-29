package com.farmconnect.backend.service;

import com.farmconnect.backend.dto.AuthResponse;
import com.farmconnect.backend.dto.LoginRequest;
import com.farmconnect.backend.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    String verifyOtp(String email, String otp);
    String resendOtp(String email);
}