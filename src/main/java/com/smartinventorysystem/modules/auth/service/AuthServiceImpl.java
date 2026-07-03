package com.smartinventorysystem.modules.auth.service;

import com.smartinventorysystem.enums.Role;
import com.smartinventorysystem.enums.Status;
import com.smartinventorysystem.exceptions.BadRequestException;
import com.smartinventorysystem.exceptions.UnauthorizedException;
import com.smartinventorysystem.modules.auth.dto.request.ActivateAccountRequest;
import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.auth.dto.request.LoginRequest;
import com.smartinventorysystem.modules.auth.dto.request.SignupRequest;
import com.smartinventorysystem.modules.auth.mapper.UserMapper;
import com.smartinventorysystem.modules.user.repository.UserRepository;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.security.JwtUtil;
import com.smartinventorysystem.security.TokenBlacklist;
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
    private final TokenBlacklist tokenBlacklist;

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
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid password");
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

    public void logout(String token) {
        tokenBlacklist.add(token);
    }

    @Override
    public void activateAccount(ActivateAccountRequest request) {

        User user = userRepository.findByActivationToken(request.getToken())
                .orElseThrow(() -> new BadRequestException("Invalid activation token"));

        // check expiry
        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Activation link expired");
        }

        // set password
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));

        // activate account
        user.setStatus(Status.ACTIVE);

        // clear token
        user.setActivationToken(null);
        user.setTokenExpiry(null);

        // Update
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

}
