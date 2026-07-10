package com.smartinventorysystem.modules.auth.service;

import com.smartinventorysystem.common.email.EmailService;
import com.smartinventorysystem.enums.Role;
import com.smartinventorysystem.enums.Status;
import com.smartinventorysystem.exceptions.BadRequestException;
import com.smartinventorysystem.exceptions.UnauthorizedException;
import com.smartinventorysystem.modules.auth.dto.request.ActivateAccountRequest;
import com.smartinventorysystem.modules.auth.dto.request.ResendActivationRequest;
import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.auth.dto.request.LoginRequest;
import com.smartinventorysystem.modules.auth.dto.request.SignupRequest;
import com.smartinventorysystem.modules.auth.mapper.AuthUserMapper;
import com.smartinventorysystem.modules.user.repository.UserRepository;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.security.JwtUtil;
import com.smartinventorysystem.security.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthUserMapper authUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenBlacklist tokenBlacklist;
    private final EmailService emailService;

    @Override
    public AuthResponse signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = authUserMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);

        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        return authUserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid password");
        }

        if (user.getStatus() != Status.ACTIVE) {
            throw new DisabledException("Your account has been deactivated.");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        AuthResponse response = authUserMapper.toResponse(user);
        response.setStatus(user.getStatus().name());
        response.setToken(token);
        return response;
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

    @Override
    public void resendActivationLink(ResendActivationRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (user.getStatus() == Status.ACTIVE) {
            throw new BadRequestException("Account is already activated");
        }

        String token = UUID.randomUUID().toString();

        user.setActivationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusHours(24));
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        emailService.sendStaffAccountCreatedEmail(
                user.getEmail(),
                user.getFullName(),
                token
        );
    }
}
