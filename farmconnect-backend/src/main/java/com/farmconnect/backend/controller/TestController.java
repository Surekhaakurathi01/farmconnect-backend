package com.farmconnect.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/auth/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth route is public");
    }
}