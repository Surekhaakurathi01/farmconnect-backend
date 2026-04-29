package com.farmconnect.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.farmconnect.backend.dto.AdminProfileDto;
import com.farmconnect.backend.dto.ExpertProfileDto;
import com.farmconnect.backend.dto.FarmerProfileDto;
import com.farmconnect.backend.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping({"/farmer/profile", "/farmer/profile/{userId}"})
    public ResponseEntity<FarmerProfileDto> createOrUpdateFarmerProfile(
            @RequestBody FarmerProfileDto dto,
            Authentication authentication,
            @PathVariable(required = false) Long userId) {
        return ResponseEntity.ok(profileService.createOrUpdateFarmerProfile(authentication.getName(), dto));
    }

    @GetMapping({"/farmer/profile", "/farmer/profile/{userId}"})
    public ResponseEntity<FarmerProfileDto> getFarmerProfile(Authentication authentication, @PathVariable(required = false) Long userId) {
        return ResponseEntity.ok(profileService.getFarmerProfile(authentication.getName()));
    }

    @PostMapping({"/expert/profile", "/expert/profile/{userId}"})
    public ResponseEntity<ExpertProfileDto> createOrUpdateExpertProfile(
            @RequestBody ExpertProfileDto dto,
            Authentication authentication,
            @PathVariable(required = false) Long userId) {
        return ResponseEntity.ok(profileService.createOrUpdateExpertProfile(authentication.getName(), dto));
    }

    @GetMapping({"/expert/profile", "/expert/profile/{userId}"})
    public ResponseEntity<ExpertProfileDto> getExpertProfile(Authentication authentication, @PathVariable(required = false) Long userId) {
        return ResponseEntity.ok(profileService.getExpertProfile(authentication.getName()));
    }

    @PostMapping({"/admin/profile", "/admin/profile/{userId}"})
    public ResponseEntity<AdminProfileDto> createOrUpdateAdminProfile(
            @RequestBody AdminProfileDto dto,
            Authentication authentication,
            @PathVariable(required = false) Long userId) {
        return ResponseEntity.ok(profileService.createOrUpdateAdminProfile(authentication.getName(), dto));
    }

    @GetMapping({"/admin/profile", "/admin/profile/{userId}"})
    public ResponseEntity<AdminProfileDto> getAdminProfile(Authentication authentication, @PathVariable(required = false) Long userId) {
        return ResponseEntity.ok(profileService.getAdminProfile(authentication.getName()));
    }
}
