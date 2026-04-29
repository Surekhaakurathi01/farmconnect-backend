package com.farmconnect.backend.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmconnect.backend.dto.AuthResponse;
import com.farmconnect.backend.dto.LoginRequest;
import com.farmconnect.backend.dto.RegisterRequest;
import com.farmconnect.backend.entity.User;
import com.farmconnect.backend.entity.VerificationToken;
import com.farmconnect.backend.enums.Role;
import com.farmconnect.backend.repository.UserRepository;
import com.farmconnect.backend.repository.VerificationTokenRepository;
import com.farmconnect.backend.security.JwtService;
import com.farmconnect.backend.service.AuthService;
import com.farmconnect.backend.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .fullName(resolveFullName(request))
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(clean(request.getPhone()))
                .location(clean(request.getLocation()))
                .role(resolveRegistrationRole(request))
                .enabled(false)
                .build();

        User savedUser = userRepository.save(user);
        String otp = generateOtp();
        saveOtp(savedUser, otp);
        sendOtp(savedUser, otp, false);

        return buildAuthResponse(savedUser, null,
                "Registration successful. OTP has been sent to your email for verification.");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(normalizeEmail(request.getEmail()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("Please verify your email with OTP before logging in.");
        }

        String token = jwtService.generateToken(user);
        return buildAuthResponse(user, token, "Login successful");
    }

    @Override
    @Transactional
    public String verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new RuntimeException("User not found"));

        VerificationToken verificationToken = verificationTokenRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("OTP not found. Please resend OTP."));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired. Please resend OTP.");
        }

        if (!verificationToken.getToken().equals(clean(otp))) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);

        return "Email verified successfully. You can now login.";
    }

    @Override
    @Transactional
    public String resendOtp(String email) {
        User user = userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isEnabled()) {
            return "User is already verified.";
        }

        String otp = generateOtp();
        saveOtp(user, otp);
        sendOtp(user, otp, true);
        return "A new OTP has been sent to your email.";
    }

    private void saveOtp(User user, String otp) {
        verificationTokenRepository.findByUser(user).ifPresent(verificationTokenRepository::delete);
        verificationTokenRepository.flush();
        verificationTokenRepository.save(VerificationToken.builder()
                .token(otp)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build());
    }

    private void sendOtp(User user, String otp, boolean resend) {
        String subject = "FarmConnect OTP Verification";
        String label = resend ? "new OTP" : "OTP";
        emailService.sendEmail(
                user.getEmail(),
                subject,
                "Hello " + user.getFullName() + ",\n\n"
                        + "Your " + label + " for FarmConnect email verification is: " + otp + "\n\n"
                        + "This OTP will expire in 10 minutes.\n\n"
                        + "Regards,\nFarmConnect Team"
        );
    }

    private AuthResponse buildAuthResponse(User user, String token, String message) {
        boolean verified = user.isEnabled();
        String accountStatus = verified ? "ACTIVE" : "PENDING_VERIFICATION";
        return AuthResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .name(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .accessToken(token)
                .message(message)
                .isEmailVerified(verified)
                .emailVerified(verified)
                .accountStatus(accountStatus)
                .status(accountStatus)
                .isAuthorized(verified)
                .requiresEmailVerification(!verified)
                .build();
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    private String resolveFullName(RegisterRequest request) {
        String fullName = clean(request.getFullName());
        if (!fullName.isEmpty()) {
            return fullName;
        }
        String name = clean(request.getName());
        if (!name.isEmpty()) {
            return name;
        }
        throw new RuntimeException("Full name is required");
    }

    private Role resolveRegistrationRole(RegisterRequest request) {
        if (request.getRole() == null) {
            throw new RuntimeException("Role is required");
        }
        return request.getRole();
    }

    private String normalizeEmail(String email) {
        return clean(email).toLowerCase();
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
