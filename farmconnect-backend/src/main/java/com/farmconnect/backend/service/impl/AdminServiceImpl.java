package com.farmconnect.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmconnect.backend.dto.AdviceResponseDto;
import com.farmconnect.backend.dto.ProductResponseDto;
import com.farmconnect.backend.dto.UserResponseDto;
import com.farmconnect.backend.dto.UserUpdateDto;
import com.farmconnect.backend.entity.Advice;
import com.farmconnect.backend.entity.Product;
import com.farmconnect.backend.entity.User;
import com.farmconnect.backend.repository.AdviceRepository;
import com.farmconnect.backend.repository.ProductRepository;
import com.farmconnect.backend.repository.UserRepository;
import com.farmconnect.backend.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AdviceRepository adviceRepository;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapUserToDto).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return mapUserToDto(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserResponseDto updateUser(Long id, UserUpdateDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (hasText(dto.getFullName())) user.setFullName(dto.getFullName().trim());
        if (hasText(dto.getName())) user.setFullName(dto.getName().trim());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getLocation() != null) user.setLocation(dto.getLocation());
        if (dto.getRole() != null) user.setRole(dto.getRole());
        if (dto.getEnabled() != null) user.setEnabled(dto.getEnabled());
        if (hasText(dto.getStatus()) || hasText(dto.getAccountStatus())) {
            String status = hasText(dto.getStatus()) ? dto.getStatus() : dto.getAccountStatus();
            user.setEnabled("ACTIVE".equalsIgnoreCase(status));
        }
        if (dto.getIsAuthorized() != null) user.setEnabled(dto.getIsAuthorized());
        return mapUserToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapProductToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @Override
    public List<AdviceResponseDto> getAllAdviceRequests() {
        return adviceRepository.findAll().stream().map(this::mapAdviceToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAdviceRequest(Long id) {
        adviceRepository.delete(adviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Advice request not found")));
    }

    private UserResponseDto mapUserToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setLocation(user.getLocation());
        dto.setRole(user.getRole());
        dto.setIsEmailVerified(user.isEnabled());
        dto.setEmailVerified(user.isEnabled());
        dto.setAccountStatus(user.isEnabled() ? "ACTIVE" : "INACTIVE");
        dto.setStatus(dto.getAccountStatus());
        dto.setIsAuthorized(user.isEnabled());
        return dto;
    }

    private ProductResponseDto mapProductToDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCropName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setPricePerUnit(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setDescription(product.getDescription());
        dto.setNotes(product.getDescription());
        dto.setLocation(product.getLocation());
        dto.setPickupLocation(product.getLocation());
        dto.setHarvestDate(product.getHarvestDate());
        dto.setAvailableFrom(product.getHarvestDate());
        dto.setCreatedAt(product.getCreatedAt() == null ? null : product.getCreatedAt().toString());
        if (product.getFarmer() != null) {
            dto.setFarmerId(product.getFarmer().getId());
            dto.setFarmerName(product.getFarmer().getFullName());
            dto.setSeller(product.getFarmer().getFullName());
        }
        return dto;
    }

    private AdviceResponseDto mapAdviceToDto(Advice advice) {
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

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
