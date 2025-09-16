package com.example.SpringBoot_SKILLUP.service;

import com.example.SpringBoot_SKILLUP.dto.RegisterRequest;
import com.example.SpringBoot_SKILLUP.dto.AuthResponse;
import com.example.SpringBoot_SKILLUP.model.User;
import com.example.SpringBoot_SKILLUP.model.Role;
import com.example.SpringBoot_SKILLUP.repository.UserRepository;
import com.example.SpringBoot_SKILLUP.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.SpringBoot_SKILLUP.dto.LoginRequest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 1. Check if user already exists
        if (userRepository.existsByEmail(request.email)) {
            throw new RuntimeException("Email already registered");
        }

        // 2. Create new user
        User user = new User();
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setFirstName(request.firstName);
        user.setLastName(request.lastName);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 3. Assign default USER role
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default USER role not found in database"));
        user.setRoles(Set.of(userRole));

        // 4. Save and generate token
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        // Search user by email
        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new RuntimeException("Email or password incorrect"));

        // Verify password
        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Email or password incorrect");
        }

        // Generate token
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}