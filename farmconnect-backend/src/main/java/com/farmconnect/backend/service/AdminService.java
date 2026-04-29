package com.farmconnect.backend.service;

import java.util.List;

import com.farmconnect.backend.dto.AdviceResponseDto;
import com.farmconnect.backend.dto.ProductResponseDto;
import com.farmconnect.backend.dto.UserResponseDto;
import com.farmconnect.backend.dto.UserUpdateDto;

public interface AdminService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto updateUser(Long id, UserUpdateDto dto);

    void deleteUser(Long id);

    List<ProductResponseDto> getAllProducts();

    void deleteProduct(Long id);

    List<AdviceResponseDto> getAllAdviceRequests();

    void deleteAdviceRequest(Long id);
}