package com.farmconnect.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmconnect.backend.dto.ProductRequestDto;
import com.farmconnect.backend.dto.ProductResponseDto;
import com.farmconnect.backend.entity.Product;
import com.farmconnect.backend.entity.User;
import com.farmconnect.backend.enums.Role;
import com.farmconnect.backend.repository.ProductRepository;
import com.farmconnect.backend.repository.UserRepository;
import com.farmconnect.backend.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto, String currentUserEmail) {
        User farmer = getRequiredUser(currentUserEmail, Role.FARMER);
        Product product = new Product();
        applyProductFields(product, dto);
        product.setFarmer(farmer);
        return mapToDto(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getProductsByFarmer(String currentUserEmail) {
        User farmer = getRequiredUser(currentUserEmail, Role.FARMER);
        return productRepository.findByFarmerId(farmer.getId()).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto, String currentUserEmail) {
        User farmer = getRequiredUser(currentUserEmail, Role.FARMER);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        ensureFarmerOwnsProduct(product, farmer.getId());
        applyProductFields(product, dto);
        return mapToDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id, String currentUserEmail) {
        User farmer = getRequiredUser(currentUserEmail, Role.FARMER);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        ensureFarmerOwnsProduct(product, farmer.getId());
        productRepository.delete(product);
    }

    private void applyProductFields(Product product, ProductRequestDto dto) {
        product.setName(firstText(dto.getName(), dto.getCropName()));
        product.setCategory(firstText(dto.getCategory(), "General"));
        product.setPrice(firstNumber(dto.getPrice(), dto.getPricePerUnit()));
        product.setQuantity(dto.getQuantity());
        product.setDescription(firstText(dto.getDescription(), dto.getNotes()));
        product.setLocation(firstText(dto.getLocation(), dto.getPickupLocation()));
        product.setHarvestDate(firstText(dto.getHarvestDate(), dto.getAvailableFrom()));
    }

    private User getRequiredUser(String email, Role role) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != role) {
            throw new RuntimeException("Access denied");
        }
        return user;
    }

    private void ensureFarmerOwnsProduct(Product product, Long farmerId) {
        if (product.getFarmer() == null || !product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("You can only modify your own products");
        }
    }

    private ProductResponseDto mapToDto(Product product) {
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

    private String firstText(String primary, String fallback) {
        if (primary != null && !primary.trim().isEmpty()) return primary.trim();
        return fallback == null ? null : fallback.trim();
    }

    private Double firstNumber(Double primary, Double fallback) {
        return primary != null ? primary : fallback;
    }
}
