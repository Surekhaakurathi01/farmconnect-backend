package com.farmconnect.backend.service.impl;

import org.springframework.stereotype.Service;

import com.farmconnect.backend.dto.AdminProfileDto;
import com.farmconnect.backend.dto.ExpertProfileDto;
import com.farmconnect.backend.dto.FarmerProfileDto;
import com.farmconnect.backend.entity.AdminProfile;
import com.farmconnect.backend.entity.ExpertProfile;
import com.farmconnect.backend.entity.FarmerProfile;
import com.farmconnect.backend.entity.User;
import com.farmconnect.backend.enums.Role;
import com.farmconnect.backend.repository.AdminProfileRepository;
import com.farmconnect.backend.repository.ExpertProfileRepository;
import com.farmconnect.backend.repository.FarmerProfileRepository;
import com.farmconnect.backend.repository.UserRepository;
import com.farmconnect.backend.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final FarmerProfileRepository farmerProfileRepository;
    private final ExpertProfileRepository expertProfileRepository;
    private final AdminProfileRepository adminProfileRepository;

    @Override
    public FarmerProfileDto createOrUpdateFarmerProfile(String currentUserEmail, FarmerProfileDto dto) {
        User user = getRequiredUser(currentUserEmail, Role.FARMER);

        FarmerProfile profile = farmerProfileRepository.findByUserId(user.getId())
                .orElseGet(() -> FarmerProfile.builder().user(user).build());

        profile.setFarmName(dto.getFarmName());
        profile.setFarmLocation(dto.getFarmLocation());
        profile.setLandArea(dto.getLandArea());
        profile.setSoilType(dto.getSoilType());
        profile.setMainCrop(dto.getMainCrop());
        profile.setIrrigationType(dto.getIrrigationType());
        profile.setUser(user);

        FarmerProfile savedProfile = farmerProfileRepository.save(profile);

        FarmerProfileDto response = new FarmerProfileDto();
        response.setUserId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setLocation(user.getLocation());
        response.setFarmName(savedProfile.getFarmName());
        response.setFarmLocation(savedProfile.getFarmLocation());
        response.setLandArea(savedProfile.getLandArea());
        response.setSoilType(savedProfile.getSoilType());
        response.setMainCrop(savedProfile.getMainCrop());
        response.setIrrigationType(savedProfile.getIrrigationType());

        return response;
    }

    @Override
    public FarmerProfileDto getFarmerProfile(String currentUserEmail) {
        User user = getRequiredUser(currentUserEmail, Role.FARMER);

        FarmerProfile profile = farmerProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Farmer profile not found"));

        FarmerProfileDto dto = new FarmerProfileDto();
        dto.setUserId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setLocation(user.getLocation());
        dto.setFarmName(profile.getFarmName());
        dto.setFarmLocation(profile.getFarmLocation());
        dto.setLandArea(profile.getLandArea());
        dto.setSoilType(profile.getSoilType());
        dto.setMainCrop(profile.getMainCrop());
        dto.setIrrigationType(profile.getIrrigationType());

        return dto;
    }

    @Override
    public ExpertProfileDto createOrUpdateExpertProfile(String currentUserEmail, ExpertProfileDto dto) {
        User user = getRequiredUser(currentUserEmail, Role.EXPERT);

        ExpertProfile profile = expertProfileRepository.findByUserId(user.getId())
                .orElseGet(() -> ExpertProfile.builder().user(user).build());

        profile.setSpecialization(dto.getSpecialization());
        profile.setQualification(dto.getQualification());
        profile.setExperienceYears(dto.getExperienceYears());
        profile.setOrganization(dto.getOrganization());
        profile.setBio(dto.getBio());
        profile.setUser(user);

        ExpertProfile savedProfile = expertProfileRepository.save(profile);

        ExpertProfileDto response = new ExpertProfileDto();
        response.setUserId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setLocation(user.getLocation());
        response.setSpecialization(savedProfile.getSpecialization());
        response.setQualification(savedProfile.getQualification());
        response.setExperienceYears(savedProfile.getExperienceYears());
        response.setOrganization(savedProfile.getOrganization());
        response.setBio(savedProfile.getBio());

        return response;
    }

    @Override
    public ExpertProfileDto getExpertProfile(String currentUserEmail) {
        User user = getRequiredUser(currentUserEmail, Role.EXPERT);

        ExpertProfile profile = expertProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Expert profile not found"));

        ExpertProfileDto dto = new ExpertProfileDto();
        dto.setUserId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setLocation(user.getLocation());
        dto.setSpecialization(profile.getSpecialization());
        dto.setQualification(profile.getQualification());
        dto.setExperienceYears(profile.getExperienceYears());
        dto.setOrganization(profile.getOrganization());
        dto.setBio(profile.getBio());

        return dto;
    }

    @Override
    public AdminProfileDto createOrUpdateAdminProfile(String currentUserEmail, AdminProfileDto dto) {
        User user = getRequiredUser(currentUserEmail, Role.ADMIN);

        AdminProfile profile = adminProfileRepository.findByUserId(user.getId())
                .orElseGet(() -> AdminProfile.builder().user(user).build());

        profile.setDepartment(dto.getDepartment());
        profile.setAccessLevel(dto.getAccessLevel());
        profile.setResponsibilities(dto.getResponsibilities());
        profile.setUser(user);

        AdminProfile savedProfile = adminProfileRepository.save(profile);

        AdminProfileDto response = new AdminProfileDto();
        response.setUserId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setLocation(user.getLocation());
        response.setDepartment(savedProfile.getDepartment());
        response.setAccessLevel(savedProfile.getAccessLevel());
        response.setResponsibilities(savedProfile.getResponsibilities());

        return response;
    }

    @Override
    public AdminProfileDto getAdminProfile(String currentUserEmail) {
        User user = getRequiredUser(currentUserEmail, Role.ADMIN);

        AdminProfile profile = adminProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Admin profile not found"));

        AdminProfileDto dto = new AdminProfileDto();
        dto.setUserId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setLocation(user.getLocation());
        dto.setDepartment(profile.getDepartment());
        dto.setAccessLevel(profile.getAccessLevel());
        dto.setResponsibilities(profile.getResponsibilities());

        return dto;
    }

    private User getRequiredUser(String email, Role role) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != role) {
            throw new RuntimeException("Access denied");
        }

        return user;
    }
}