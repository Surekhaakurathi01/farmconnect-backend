package com.farmconnect.backend.service;

import java.util.List;

import com.farmconnect.backend.dto.ProductResponseDto;
import com.farmconnect.backend.dto.UserResponseDto;

public interface PublicService {

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(Long id);

    List<UserResponseDto> getAllExperts();

    List<UserResponseDto> getAllFarmers();
}