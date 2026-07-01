package com.smartinventorysystem.modules.auth.service;

import com.smartinventorysystem.enums.Role;
import com.smartinventorysystem.enums.Status;
import com.smartinventorysystem.modules.auth.dto.AuthResponse;
import com.smartinventorysystem.modules.auth.dto.LoginRequest;
import com.smartinventorysystem.modules.auth.dto.SignupRequest;
import com.smartinventorysystem.modules.auth.mapper.UserMapper;
import com.smartinventorysystem.modules.auth.repository.UserRepository;
import com.smartinventorysystem.modules.user.dto.UpdateProfileRequest;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);

        return AuthResponse.builder()
                .userId(saved.getUserID())
                .fullName(saved.getFullName())
                .email(saved.getEmail())
                .role(saved.getRole().name())
                .message("Signup successful")
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .userId(user.getUserID())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("Login successful")
                .token(token)
                .build();
    }

    @Override
    public AuthResponse updateProfile(Integer userId, UpdateProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // update only allowed fields (
        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {

            // prevent duplicate email
            boolean emailExists = userRepository.existsByEmail(request.getEmail());
            if (emailExists && !user.getEmail().equals(request.getEmail())) {
                throw new RuntimeException("Email already in use");
            }

            user.setEmail(request.getEmail());
        }

        user.setUpdatedAt(LocalDateTime.now());

        User updated = userRepository.save(user);

        return AuthResponse.builder()
                .userId(updated.getUserID())
                .fullName(updated.getFullName())
                .email(updated.getEmail())
                .role(updated.getRole().name())
                .message("Profile updated successfully")
                .build();
    }
}
