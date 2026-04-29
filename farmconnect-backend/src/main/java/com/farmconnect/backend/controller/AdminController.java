package com.farmconnect.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.farmconnect.backend.dto.AdviceResponseDto;
import com.farmconnect.backend.dto.ProductResponseDto;
import com.farmconnect.backend.dto.UserResponseDto;
import com.farmconnect.backend.dto.UserUpdateDto;
import com.farmconnect.backend.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto dto) {
        return ResponseEntity.ok(adminService.updateUser(id, dto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(adminService.getAllProducts());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/advice-requests")
    public ResponseEntity<List<AdviceResponseDto>> getAllAdviceRequests() {
        return ResponseEntity.ok(adminService.getAllAdviceRequests());
    }

    @DeleteMapping("/advice-requests/{id}")
    public ResponseEntity<String> deleteAdviceRequest(@PathVariable Long id) {
        adminService.deleteAdviceRequest(id);
        return ResponseEntity.ok("Advice request deleted successfully");
    }
}