package com.farmconnect.backend.service;

import java.util.List;

import com.farmconnect.backend.dto.ProductRequestDto;
import com.farmconnect.backend.dto.ProductResponseDto;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto dto, String currentUserEmail);
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> getProductsByFarmer(String currentUserEmail);
    ProductResponseDto updateProduct(Long id, ProductRequestDto dto, String currentUserEmail);
    void deleteProduct(Long id, String currentUserEmail);
}
