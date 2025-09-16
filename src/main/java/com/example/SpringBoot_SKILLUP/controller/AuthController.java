package com.example.SpringBoot_SKILLUP.controller;

import com.example.SpringBoot_SKILLUP.dto.RegisterRequest;
import com.example.SpringBoot_SKILLUP.dto.AuthResponse;
import com.example.SpringBoot_SKILLUP.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SpringBoot_SKILLUP.dto.LoginRequest;
import com.example.SpringBoot_SKILLUP.dto.AuthResponse;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
