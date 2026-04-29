package com.farmconnect.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.farmconnect.backend.dto.ProductResponseDto;
import com.farmconnect.backend.dto.UserResponseDto;
import com.farmconnect.backend.service.PublicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final PublicService publicService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(publicService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(publicService.getProductById(id));
    }

    @GetMapping("/experts")
    public ResponseEntity<List<UserResponseDto>> getAllExperts() {
        return ResponseEntity.ok(publicService.getAllExperts());
    }

    @GetMapping("/farmers")
    public ResponseEntity<List<UserResponseDto>> getAllFarmers() {
        return ResponseEntity.ok(publicService.getAllFarmers());
    }
}