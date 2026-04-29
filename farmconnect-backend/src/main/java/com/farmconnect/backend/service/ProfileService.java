package com.farmconnect.backend.service;

import com.farmconnect.backend.dto.AdminProfileDto;
import com.farmconnect.backend.dto.ExpertProfileDto;
import com.farmconnect.backend.dto.FarmerProfileDto;

public interface ProfileService {

    FarmerProfileDto createOrUpdateFarmerProfile(String currentUserEmail, FarmerProfileDto dto);
    FarmerProfileDto getFarmerProfile(String currentUserEmail);

    ExpertProfileDto createOrUpdateExpertProfile(String currentUserEmail, ExpertProfileDto dto);
    ExpertProfileDto getExpertProfile(String currentUserEmail);

    AdminProfileDto createOrUpdateAdminProfile(String currentUserEmail, AdminProfileDto dto);
    AdminProfileDto getAdminProfile(String currentUserEmail);
}
