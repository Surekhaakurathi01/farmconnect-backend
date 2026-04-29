package com.farmconnect.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expert")
public class ExpertController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> expertDashboard() {
        return ResponseEntity.ok("Welcome Expert Dashboard");
    }
}