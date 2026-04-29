package com.farmconnect.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.farmconnect.backend.dto.ProductRequestDto;
import com.farmconnect.backend.dto.ProductResponseDto;
import com.farmconnect.backend.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/farmer")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping({"/products", "/products/{farmerId}"})
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody ProductRequestDto dto,
            Authentication authentication) {
        return ResponseEntity.ok(productService.createProduct(dto, authentication.getName()));
    }

    @GetMapping({"/products", "/products/{farmerId}"})
    public ResponseEntity<List<ProductResponseDto>> getProductsByFarmer(Authentication authentication, @PathVariable(required = false) Long farmerId) {
        return ResponseEntity.ok(productService.getProductsByFarmer(authentication.getName()));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto dto,
            Authentication authentication) {
        return ResponseEntity.ok(productService.updateProduct(id, dto, authentication.getName()));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id, Authentication authentication) {
        productService.deleteProduct(id, authentication.getName());
        return ResponseEntity.ok("Product deleted successfully");
    }
}
