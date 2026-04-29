package com.farmconnect.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmconnect.backend.dto.ProductResponseDto;
import com.farmconnect.backend.dto.UserResponseDto;
import com.farmconnect.backend.entity.Product;
import com.farmconnect.backend.entity.User;
import com.farmconnect.backend.enums.Role;
import com.farmconnect.backend.repository.ProductRepository;
import com.farmconnect.backend.repository.UserRepository;
import com.farmconnect.backend.service.PublicService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicServiceImpl implements PublicService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapProductToDto).collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        return mapProductToDto(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @Override
    public List<UserResponseDto> getAllExperts() {
        return userRepository.findByRole(Role.EXPERT).stream().map(this::mapUserToDto).collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> getAllFarmers() {
        return userRepository.findByRole(Role.FARMER).stream().map(this::mapUserToDto).collect(Collectors.toList());
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
        dto.setAccountStatus(user.isEnabled() ? "ACTIVE" : "PENDING_VERIFICATION");
        dto.setStatus(dto.getAccountStatus());
        dto.setIsAuthorized(user.isEnabled());
        return dto;
    }
}
